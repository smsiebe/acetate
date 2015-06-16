package org.geoint.acetate.data.transform;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if there was a problem converting data from one type to another.
 */
public class DataConversionException extends AcetateException {

    public DataConversionException() {
    }

    public DataConversionException(String message) {
        super(message);
    }

    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataConversionException(Throwable cause) {
        super(cause);
    }

}
