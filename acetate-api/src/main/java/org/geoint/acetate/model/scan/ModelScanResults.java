package org.geoint.acetate.model.scan;

import org.geoint.acetate.DomainRegistry;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ModelComponent;

/**
 * Results from a domain model scan operation.
 *
 * @see ModelScanner
 * @see DomainRegistry
 */
public interface ModelScanResults {

    /**
     * Indicates if the scan completed successfully.
     *
     * @return true if scan completed successfully, otherwise false
     */
    boolean completedSuccessfully();

    /**
     * Error thrown by scanner if it did not complete successfully.
     *
     * @return contains Throwable if the scanner did not complete successfully
     */
    Optional<Throwable> getCause();

    /**
     * Components found by the scanner.
     *
     * @return scanner discovered components
     */
    Collection<ModelComponent> getComponents();

    /**
     * How long the scanner took to complete.
     *
     * @return duration of scan, even if it was unsuccessful
     */
    Duration getScanDuration();

    /**
     * Type of scanner used.
     *
     * @return type of scanner
     */
    Class<? extends ModelScanner> getScannerType();
}
