package org.geoint.acetate.impl.model.scan;

import org.geoint.acetate.DomainRegistry;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Manages asynchronous domain model scans.
 *
 * Instances of this class are thread-safe.
 */
public class ModelScanManager implements Runnable {

    private Map<ModelScanTask, Future<ModelScanResults>> runningTasks;
    private final DomainRegistry registry;
    private final DomainModelFactory dmf;
    private final ExecutorService executor;

    private static final long DEFAULT_SCAN_TIMEOUT
            = TimeUnit.MINUTES.toMillis(5);
    private static final Logger logger
            = Logger.getLogger(ModelScanManager.class.getName());

    public ModelScanManager(DomainRegistry registry, DomainModelFactory dmf,
            ExecutorService executor) {
        this.registry = registry;
        this.dmf = dmf;
        this.executor = executor;
        runningTasks = new ConcurrentHashMap<>();
    }

    /**
     * model scanner management service
     */
    @Override
    public void run() {
        for (;;) {
            try {
                //attempt to shutdown any running task over its configured timeout
                runningTasks.entrySet().stream()
                        .filter((e) -> (System.currentTimeMillis() - e.getKey().startMillis)
                                > e.getKey().timeoutMillis)
                        .forEach((e) -> {
                            logger.log(Level.FINE, "Attempting to shutdown task "
                                    + "''{0}'', timeout.", e.getKey().toString());
                            e.getValue().cancel(true);
                        });
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                if (Thread.interrupted()) {
                    logger.log(Level.INFO, "Shutting down model scan mamanger; "
                            + "[" + runningTasks.size() + "] scans pending.", ex);
                    runningTasks.values().stream().forEach((t) -> t.cancel(true));
                }
            }
        }
    }

    public Future<ModelScanResults> execute(
            ModelScanner scanner) {
        return execute(new ModelScanTask(scanner));
    }

    public Future<? extends ModelScanResults> execute(
            ModelScanner scanner,
            long timeout,
            TimeUnit timeoutUnit) {
        return execute(new ModelScanTask(scanner,
                timeoutUnit.toMillis(timeout))
        );
    }

    private Future<ModelScanResults> execute(ModelScanTask task) {
        final Future<ModelScanResults> scan = executor.submit(task);
        logger.log(Level.FINE, "Scanning for model components " + scan.toString());
        runningTasks.put(task, scan);
        return scan;
    }

    /**
     * Task which executes the ModelScanner, registers domain model results with
     * the provided registry, and provides the scan results.
     *
     * ModelScanTask removes itself from the runningTasks when complete.
     */
    private final class ModelScanTask
            implements ModelScanResults, Callable<ModelScanResults> {

        private final ModelScanner scanner;
        private final long timeoutMillis;
        private long startMillis;
        private Duration duration;
        private int numComponentsFound;
        private Optional<Throwable> error;

        public ModelScanTask(ModelScanner scanner) {
            this(scanner, DEFAULT_SCAN_TIMEOUT);
        }

        private ModelScanTask(ModelScanner scanner,
                long timeoutMillis) {
            this.scanner = scanner;
            this.timeoutMillis = timeoutMillis;
        }

        @Override
        public ModelScanTask call() {

            final Collection<ModelComponent> components = new ArrayList<>();

            startMillis = System.currentTimeMillis();

            try {
                //do scan, adding components to components collection
                scanner.scan((n, v, c) -> components.add(c));

                //convert and register domain models
                for (DomainModel dm : dmf.fromComponents(components)) {
                    registry.register(dm);
                }

                //no errors
                error = Optional.empty();  //no error
            } catch (ModelScanException | ModelException ex) {
                logger.log(Level.WARNING, "Unable to complete model scan task '"
                        + this.toString() + "'", ex);
                error = Optional.of(ex);
            }

            //register results
            this.duration = Duration.ofMillis(System.currentTimeMillis() - startMillis);
            this.numComponentsFound = components.size();

            //remove itself from running tasks
            runningTasks.remove(this);

            return this;
        }

        @Override
        public boolean completedSuccessfully() {
            return !error.isPresent();
        }

        @Override
        public Optional<Throwable> getCause() {
            return error;
        }

        @Override
        public int getNumComponentsFound() {
            return numComponentsFound;
        }

        @Override
        public Duration getScanDuration() {
            return duration;
        }

        @Override
        public Class<? extends ModelScanner> getScannerType() {
            return scanner.getClass();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Model scanner '")
                    .append(scanner.getClass().getName())
                    .append("'");
            if (startMillis > 0) {
                sb.append(" started ")
                        .append(Instant.ofEpochMilli(startMillis).toString());
            }
            return sb.toString();
        }
    }
//    
//    /*
//     * async component listener notifier
//     */
//    private class AsyncComponentNotifier
//            implements Callable<Collection<ModelComponent>> {
//
//        private final BlockingQueue<ModelComponent> queue;
//        private final Collection<ModelComponentListener> defensiveListeners;
//
//        public AsyncComponentNotifier(BlockingQueue<ModelComponent> queue,
//                ModelComponentListener[] listeners) {
//            this.queue = queue;
//
//            //defensive copy listeners to new collection, preventing 
//            //concurrency issues
//            this.defensiveListeners = Arrays.asList(listeners);
//        }
//
//        @Override
//        public Collection<ModelComponent> call() {
//            final Collection<ModelComponent> components = new ArrayList<>();
//            for (;;) {
//                try {
//                    ModelComponent c = queue.take();
//
//                    //check for shutdown poison pill
//                    if (c instanceof NotifierShutdownPoisonPill) {
//                        logger.log(Level.FINE, "Gracefully shutting down async"
//                                + " model component notifier: "
//                                + ((NotifierShutdownPoisonPill) c).getReason());
//                    }
//
//                    //first validate each listener
//                    for (ModelComponentListener l : defensiveListeners) {
//                        try {
//                            l.validate(c);
//                        } catch (ModelComponentRejectedException ex) {
//                            //log and skip the component
//                            logger.log(Level.FINE, "Model component '"
//                                    + c.getAddress()
//                                    + "' was rejected by "
//                                    + "component listener '"
//                                    + l.getClass().getName()
//                                    + "'.  Component will not be added to the "
//                                    + "model.", ex);
//                            continue;
//                        }
//                    }
//
//                    //component passed validation, add to model and notify 
//                    //each listener
//                    components.add(c);
//                    defensiveListeners.stream()
//                            .forEach((l) -> l.component(
//                                            c.getAddress().getDomainName(),
//                                            c.getAddress().getDomainVersion(),
//                                            c)
//                            );
//                } catch (InterruptedException ex) {
//                    if (Thread.interrupted() && exec.isShutdown()) {
//                        logger.log(Level.WARNING, "Component model scan "
//                                + "shutting down.", ex);
//                        break;
//                    }
//                }
//            }
//            return components;
//        }
//    }
//
//    /**
//     * Not really a ModelComponent implementation, this class is used as a
//     * "poison pill" to let AsyncComponentNotifier know that it can shutdown
//     * (there are not more model components).
//     */
//    private final class NotifierShutdownPoisonPill implements ModelComponent {
//
//        private final String reason;
//
//        public NotifierShutdownPoisonPill(String reason) {
//            this.reason = reason;
//        }
//
//        public String getReason() {
//            return reason;
//        }
//
//        @Override
//        public ComponentAddress getAddress() {
//            throw new UnsupportedOperationException("Method of poison pill "
//                    + "was unexpectedly called.");
//        }
//
//        @Override
//        public String getName() {
//            throw new UnsupportedOperationException("Method of poison pill "
//                    + "was unexpectedly called.");
//        }
//
//        @Override
//        public Optional<String> getDescription() {
//            throw new UnsupportedOperationException("Method of poison pill "
//                    + "was unexpectedly called.");
//        }
//
//        @Override
//        public Collection<? extends ComponentAttribute> getAttributes() {
//            throw new UnsupportedOperationException("Method of poison pill "
//                    + "was unexpectedly called.");
//        }
//
//    }
}
