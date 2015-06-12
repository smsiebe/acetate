package org.geoint.acetate.model.scan;

/**
 * Scans for domain model components, notifying listeners and returning
 * validated components.
 *
 */
public interface ModelScanner {

    /**
     * Scans for domain model components and notifies listeners based on its
     * contract.
     *
     * @param listener
     */
    void scan(ModelComponentListener listener);

}
