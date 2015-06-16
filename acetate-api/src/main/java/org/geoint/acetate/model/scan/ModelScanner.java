package org.geoint.acetate.model.scan;

/**
 * Scans for domain model components, notifying listeners and returning
 * validated components.
 *
 */
@FunctionalInterface
public interface ModelScanner {

    /**
     * Scans for domain model components and notifies listeners based on it the
     * scan listener lifecycle contract.
     *
     * Long-running scan tasks should perodically check the threads interrupt to
     * see if has been requested to shutdown.
     *
     * @param listeners scan lifecycle callback listeners
     * @throws ModelScanException thrown if the scan could not complete
     */
    void scan(ModelComponentListener... listeners) throws ModelScanException;

}
