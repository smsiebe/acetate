package org.geoint.acetate.data.common;

/**
 * Thrown when a comparison value against a range is attempted by the range
 * limit required was null.
 */
public class UnboundedRangeException extends Exception {

    public UnboundedRangeException() {
    }

    public UnboundedRangeException(String message) {
        super(message);
    }

    public UnboundedRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnboundedRangeException(Throwable cause) {
        super(cause);
    }

}
