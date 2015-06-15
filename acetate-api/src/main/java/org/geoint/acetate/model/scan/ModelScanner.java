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
     * @param listeners scan lifecycle callback listeners
     * @throws ModelScanException thrown if the scan could not complete
     */
    void scan(ModelComponentListener... listeners) throws ModelScanException;

}
