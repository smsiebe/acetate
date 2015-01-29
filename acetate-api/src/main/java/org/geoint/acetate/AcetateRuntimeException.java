package org.geoint.acetate;

/**
 * Base exception class for all acetate runtime exceptions.
 */
public abstract class AcetateRuntimeException extends RuntimeException
        implements AcetateThrowable {

    public AcetateRuntimeException() {
    }

    public AcetateRuntimeException(String message) {
        super(message);
    }

    public AcetateRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcetateRuntimeException(Throwable cause) {
        super(cause);
    }

    public AcetateRuntimeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
