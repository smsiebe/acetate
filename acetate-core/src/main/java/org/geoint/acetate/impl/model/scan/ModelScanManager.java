package org.geoint.acetate.impl.model.scan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.scan.ModelScanListener;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Manages asynchronous {@link ModelScanner domain model component scan} tasks.
 *
 * The provided executor is used both to execute scans as well as the management
 * of the scan tasks.
 *
 * Instances of this class are thread-safe.
 */
public class ModelScanManager {

    private final ExecutorService executor;
    private final Map<ModelScanTask, Future<? extends ModelScanResults>> runningTasks;

    /**
     * Used to determine if a scan task monitor is running
     */
    private static volatile boolean monitorRunning = false;
    private static final long DEFAULT_SCAN_TIMEOUT
            = TimeUnit.MINUTES.toMillis(5);
    private static final Logger logger
            = Logger.getLogger(ModelScanManager.class.getName());

    public ModelScanManager(ExecutorService executor) {
        this.executor = executor;
        runningTasks = new ConcurrentHashMap<>();
    }

    public Future<ModelScanResults> execute(
            ModelScanner scanner, ModelScanListener... listeners) {
        return execute(new ModelScanTask(DEFAULT_SCAN_TIMEOUT, scanner, listeners));
    }

    public Future<ModelScanResults> execute(
            long timeout,
            TimeUnit timeoutUnit,
            ModelScanner scanner,
            ModelScanListener... listeners) {
        return execute(new ModelScanTask(
                timeoutUnit.toMillis(timeout),
                scanner, listeners));
    }

    private Future<ModelScanResults> execute(ModelScanTask task) {
        final Future<ModelScanResults> scan = executor.submit(task);

        logger.log(Level.FINE, "Scanning for model components {0}",
                scan.toString());

        runningTasks.put(task, scan);

        //start task monitor, if not running already
        if (!monitorRunning) {
            monitorRunning = true;

            //start monitor
            executor.submit(new ScanTaskMonitor());
        }

        return scan;
    }

    /**
     * Scan monitor task which ensures tasks do not exceed its configured
     * timeout.
     *
     */
    private class ScanTaskMonitor implements Runnable {

        /**
         * model scanner management service
         */
        @Override
        public void run() {
            logger.log(Level.FINE, "Starting scan task monitor.");

            while (!runningTasks.isEmpty()) {
                try {
                    //remove any running tasks that are done
                    //attempt to shutdown any running task over its configured timeout

                    runningTasks.entrySet().stream()
                            .filter((e) -> {
                                if (e.getValue().isDone()) {
                                    runningTasks.remove(e.getKey());
                                    return false;
                                }
                                return true;
                            })
                            .filter((e) -> e.getKey().isTimeout())
                            .forEach((e) -> {
                                logger.log(Level.FINE, "Attempting to shutdown task "
                                        + "''{0}'', timeout.", e.getKey().toString());
                                e.getValue().cancel(true);
                            });
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    if (Thread.interrupted()) {
                        break; //shutdown
                    }
                }
            }

            //shutdown monitor 
            logger.log(Level.INFO, "Shutting down model scan monitor; [{0}] "
                    + "scans pending.", runningTasks.size());
            runningTasks.values().stream().forEach((t) -> t.cancel(true));
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
//                ModelScanListener[] listeners) {
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
//                    for (ModelScanListener l : defensiveListeners) {
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
