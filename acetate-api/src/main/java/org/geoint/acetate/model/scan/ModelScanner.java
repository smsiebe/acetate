package org.geoint.acetate.model.scan;

/**
 * Scans for domain model components, notifying listeners and returning
 * validated components.
 *
 * The ModelScanner can be provided to a {@link DomainRegistry} to register new
 * domain models or domain model components (if supported).
 *
 * @see DomainRegistry
 */
@FunctionalInterface
public interface ModelScanner {

    /**
     * Starts scan for domain model components, notifying listeners based on it
     * the scan listener lifecycle contract.
     *
     * Long-running scan tasks should perodically check the threads interrupt to
     * see if has been requested to shutdown.
     *
     * Of note, all scanners must provide immutable and thread-safe model
     * components to listeners.
     *
     * @param listeners scan lifecycle callback listeners
     * @throws ModelScanException thrown if the scan could not complete
     */
    void scan(ModelScanListener... listeners) throws ModelScanException;

}
