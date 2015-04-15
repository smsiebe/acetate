package org.geoint.acetate.transform;

/**
 *
 */
public abstract class DataTransformException extends Exception {

    public DataTransformException() {
    }

    public DataTransformException(String message) {
        super(message);
    }

    public DataTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTransformException(Throwable cause) {
        super(cause);
    }

}
