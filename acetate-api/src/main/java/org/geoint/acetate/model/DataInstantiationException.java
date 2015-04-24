package org.geoint.acetate.model;

import gov.ic.geoint.acetate.AcetateException;

/**
 * Thrown if there are problems instantiating a java object instance of the
 * data.
 *
 * This exception can be thrown directly, or a more specific (subtype) exception
 * may be thrown providing more details as to why the object could not be
 * instantiated.
 */
public class DataInstantiationException extends AcetateException {

    private final DataType<?> dataType;

    public DataInstantiationException(DataType<?> dataType) {
        this.dataType = dataType;
    }

    public DataInstantiationException(DataType<?> dataType, String message) {
        super(message);
        this.dataType = dataType;
    }

    public DataInstantiationException(DataType<?> dataType, String message, Throwable cause) {
        super(message, cause);
        this.dataType = dataType;
    }

    public DataInstantiationException(DataType<?> dataType, Throwable cause) {
        super(cause);
        this.dataType = dataType;
    }

    public DataType<?> getDataType() {
        return dataType;
    }

}
