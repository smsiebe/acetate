package org.geoint.acetate.model;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when a DataType could not interact with the data provided.
 */
public class InvalidDataTypeException extends AcetateException {

    private final Class<? extends DataType> expectedDataType;

    public InvalidDataTypeException(Class<? extends DataType> expectedDataType) {
        this.expectedDataType = expectedDataType;
    }

    public InvalidDataTypeException(Class<? extends DataType> expectedDataType,
            String message) {
        super(message);
        this.expectedDataType = expectedDataType;
    }

    public InvalidDataTypeException(Class<? extends DataType> expectedDataType,
            String message, Throwable cause) {
        super(message, cause);
        this.expectedDataType = expectedDataType;
    }

    public InvalidDataTypeException(Class<? extends DataType> expectedDataType,
            Throwable cause) {
        super(cause);
        this.expectedDataType = expectedDataType;
    }

    public Class<? extends DataType> getExpectedDataType() {
        return expectedDataType;
    }

}
