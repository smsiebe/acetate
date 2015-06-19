package org.geoint.acetate.impl.model.scan;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanListener;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Task which executes the ModelScanner, registers domain model results with the
 * provided registry, and provides the scan results.
 *
 *
 * @see ModelScanManager
 */
public class ModelScanTask
        implements Callable<ModelScanResults> {

    //execution context
    private final ModelScanner scanner;
    private final ModelScanListener[] listeners;
    private final long timeoutMillis;
    private ModelScanTaskResults results;

    private static final Logger logger
            = Logger.getLogger(ModelScanTask.class.getName());

    public ModelScanTask(
            long timeoutMillis,
            ModelScanner scanner,
            ModelScanListener[] listeners) {
        this.scanner = scanner;
        this.timeoutMillis = timeoutMillis;
        this.listeners = listeners;
    }

    @Override
    public ModelScanResults call() {
        results = new ModelScanTaskResults();

        try {
            //do scan, adding listener to count components
            ModelScanListener[] withCounter
                    = Arrays.copyOf(listeners, listeners.length + 1);
            //withCollector[withCollector.length] = (n, v, c) -> results.components.add(c);
            withCounter[withCounter.length] = (c) -> results.incrementComponentCount();
            scanner.scan(withCounter);

        } catch (ModelScanException ex) {
            logger.log(Level.WARNING, "Unable to complete model scan task '"
                    + this.toString() + "'", ex);
            results.error = Optional.of(ex);
        }

        //register results
        results.duration = Duration.ofMillis(System.currentTimeMillis()
                - results.startMillis);

        return results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task for model scanner '")
                .append(scanner.getClass().getName())
                .append("'");
        return sb.toString();
    }

    /**
     * Determine if the task has exceeded its timeout
     *
     * @return true if the task has run longer than its configured timeout
     */
    boolean isTimeout() {
        return (results == null)
                ? false
                : (System.currentTimeMillis() - results.startMillis) > timeoutMillis;
    }

    private class ModelScanTaskResults implements ModelScanResults {

        //results data
        private long startMillis;
        private Duration duration;
        private Optional<Throwable> error = Optional.empty();
        private int numComponents = 0;

        public ModelScanTaskResults() {
            startMillis = System.currentTimeMillis();
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
        public Duration getScanDuration() {
            return (duration != null)
                    ? duration //task complete, duration is set
                    : (startMillis != 0)
                            //task has started, so there is a "current" duration
                            ? Duration.ofMillis(System.currentTimeMillis() - startMillis)
                            : null; //task hasn't started yet, no duration
        }

        @Override
        public Class<? extends ModelScanner> getScannerType() {
            return scanner.getClass();
        }

        private void incrementComponentCount() {
            numComponents++;
        }

        @Override
        public int getNumComponentsDiscovered() {
            return numComponents;
        }
    }
}
