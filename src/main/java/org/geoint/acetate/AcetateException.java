package org.geoint.acetate;

/**
 * Top-level library checked exception.
 */
public abstract class AcetateException extends Exception {

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
