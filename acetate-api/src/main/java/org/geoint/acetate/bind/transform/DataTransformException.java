package org.geoint.acetate.bind.transform;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if there was a problem converting data from form to another.
 */
public class DataTransformException extends AcetateException {

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
