package org.geoint.acetate.impl.model.scan;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.scan.ModelComponentListener;
import org.geoint.acetate.model.scan.ModelComponentRejectedException;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanResults;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * ModelScanner decorator which executes the scan asynchronously and notifies
 * the scan listeners asynchronously.
 *
 */
public final class AsyncModelScanner implements ModelScanner {

    private final ExecutorService exec;
    private final ModelScanner scanner;
    private final long scanTimeoutMillis;



    /**
     * Asynchronously executes the scan, returning immediately.
     */
    @Override
    public void scan(ModelComponentListener... listeners) 
            throws ModelScanException {

        
    }
    
    /**
     * Failed async timeout results
     */
    private class AysncTimeoutScanResults implements ModelScanResults {

        private final Class<? extends ModelScanner> scanType;
        private final Duration duration;
        
        private AysncTimeoutScanResults(Class<? extends ModelScanner> scanType,
                long duration) {
            this.scanType = scanType;
            this.duration = Duration.ofMillis(duration);
        }

        @Override
        public boolean completedSuccessfully() {
            return false;
        }

        @Override
        public Optional<Throwable> getCause() {
            return Optional.empty();
        }

        @Override
        public int getNumComponentsFound() {
            return null;
        }

        @Override
        public Duration getScanDuration() {

        }

        @Override
        public Class<? extends ModelScanner> getScannerType() {

        }
        
    }
    
}
