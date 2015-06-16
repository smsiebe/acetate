package org.geoint.acetate.model.scan;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when a {@link ModelScanner} terminally fails, explaining the cause.
 */
public class ModelScanException extends AcetateException {

    private final Class<? extends ModelScanner> scannerType;

    public ModelScanException(Class<? extends ModelScanner> scannerType) {
        this.scannerType = scannerType;
    }

    public ModelScanException(Class<? extends ModelScanner> scannerType, String message) {
        super(message);
        this.scannerType = scannerType;
    }

    public ModelScanException(Class<? extends ModelScanner> scannerType, String message, Throwable cause) {
        super(message, cause);
        this.scannerType = scannerType;
    }

    public ModelScanException(Class<? extends ModelScanner> scannerType, Throwable cause) {
        super(cause);
        this.scannerType = scannerType;
    }

    public Class<? extends ModelScanner> getScannerType() {
        return scannerType;
    }

}
