package org.geoint.acetate;

/**
 * Base exception class for all acetate checked exceptions.
 */
public abstract class AcetateException extends Exception
        implements AcetateThrowable {

    public AcetateException() {
    }

    public AcetateException(String message) {
        super(message);
    }

    public AcetateException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcetateException(Throwable cause) {
        super(cause);
    }

}
