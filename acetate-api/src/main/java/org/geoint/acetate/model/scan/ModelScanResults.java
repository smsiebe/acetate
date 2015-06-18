package org.geoint.acetate.model.scan;

import org.geoint.acetate.DomainRegistry;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.DomainModel;

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
     * Domain models found by the scanner.
     *
     * Domain models returned by a scanner are not
     * {@link DomainRegistry registered}.
     *
     * @return unregistered scanner discovered domain models
     */
    Collection<DomainModel> getModels();

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
