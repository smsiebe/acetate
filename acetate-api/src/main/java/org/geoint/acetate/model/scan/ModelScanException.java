package org.geoint.acetate.model.scan;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if a model scan operation fails.
 */
public class ModelScanException extends AcetateException {

    private final ModelScanResults scanResults;

    public ModelScanException(ModelScanResults scanResults) {
        super("Scanner '" + scanResults.getScannerType() + "' failed scanning "
                + "the domain model", scanResults.getCause().get());
        this.scanResults = scanResults;
    }

    public ModelScanResults getScanResults() {
        return scanResults;
    }

}
