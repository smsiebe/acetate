package org.geoint.acetate.bind;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if there are problems instantiating a java object instance of the
 * data.
 *
 * This exception can be thrown directly, or a more specific (subtype) exception
 * may be thrown providing more details as to why the object could not be
 * instantiated.
 */
public class DataBindException extends AcetateException {

    private final Class<?> dataType;

    public DataBindException(Class<?> dataType) {
        this.dataType = dataType;
    }

    public DataBindException(Class<?> dataType, String message) {
        super(message);
        this.dataType = dataType;
    }

    public DataBindException(Class<?> dataType, String message,
            Throwable cause) {
        super(message, cause);
        this.dataType = dataType;
    }

    public DataBindException(Class<?> dataType, Throwable cause) {
        super(cause);
        this.dataType = dataType;
    }

    /**
     * Data type attempting to be constructed.
     *
     * @return type definition
     */
    public Class<?> getDataType() {
        return dataType;
    }

}
