package org.geoint.acetate.model.scan;

import gov.ic.geoint.acetate.DomainRegistry;
import java.time.Duration;
import java.util.Optional;

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
     * Number of domain model components found by scanner.
     *
     * @return number of domain model components found, which may be incomplete
     * if the result did not complete successfully
     */
    int getNumComponentsFound();

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
