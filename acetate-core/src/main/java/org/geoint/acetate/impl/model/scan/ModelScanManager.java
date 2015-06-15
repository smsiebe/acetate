package org.geoint.acetate.impl.model.scan;

import gov.ic.geoint.acetate.DomainRegistry;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.scan.ModelComponentListener;
import org.geoint.acetate.model.scan.ModelComponentRejectedException;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Manages multiple domain model on a separate thread.
 *
 * Instances of this class are thread-safe.
 */
public class ModelScanManager {

    private Collection<Future<ModelScanTask>> runningTasks;

    private static final Logger logger
            = Logger.getLogger(ModelScanManager.class.getName());

    public Future<? extends ModelScanResults> execute(ExecutorService exec,
            DomainRegistry registry,
            DomainModelFactory dmf,
            ModelScanner scanner) {
        return execute(exec, new ModelScanTask(scanner, registry, dmf));
    }

    public Future<? extends ModelScanResults> execute(
            ExecutorService exec,
            DomainRegistry registry,
            DomainModelFactory dmf,
            ModelScanner scanner,
            long timeout,
            TimeUnit timeoutUnit) {
        return execute(exec,
                new ModelScanTask(scanner,
                        timeoutUnit.toMillis(timeout),
                        registry,
                        dmf)
        );

    }

    private Future<ModelScanTask> execute(ExecutorService exec,
            ModelScanTask task) {
        final Future<ModelScanTask> scan = exec.submit(task);
        runningTasks.add(scan);
        return scan;
    }

    private static final class ModelScanTask
            implements ModelScanResults, Callable<ModelScanTask> {

        private final ModelScanner scanner;
        private final long timeoutMillis;
        private final DomainRegistry registry;
        private final DomainModelFactory dmf;
        private Duration duration;
        private int numComponentsFound;
        private Optional<Throwable> error;

        private static final long DEFAULT_SCAN_TIMEOUT
                = TimeUnit.MINUTES.toMillis(5);

        public ModelScanTask(ModelScanner scanner,
                DomainRegistry registry, DomainModelFactory dmf) {
            this(scanner, DEFAULT_SCAN_TIMEOUT, registry, dmf);
        }

        private ModelScanTask(ModelScanner scanner,
                long timeoutMillis,
                DomainRegistry registry,
                DomainModelFactory dmf) {
            this.scanner = scanner;
            this.timeoutMillis = timeoutMillis;
            this.registry = registry;
            this.dmf = dmf;
        }

        @Override
        public ModelScanTask call() throws Exception {

            final Collection<ModelComponent> components = new ArrayList<>();

            final long startMillis = System.currentTimeMillis();
            
            //do scan
            scanner.scan((n,v,c) -> components.add(c));
            
            while (!scan.isDone()) {
                long duration = System.currentTimeMillis() - startMillis;
                if (duration > scanTimeoutMillis) {
                    throw new ModelScanException(new AysncTimeoutScanResults(
                            scanner.getClass(), duration));
                }
                Thread.sleep(1000);
            }

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
