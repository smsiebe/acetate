package org.geoint.acetate.model;

import gov.ic.geoint.acetate.AcetateException;

/**
 * Thrown when the data type provided is not valid for the data model.
 */
public class InvalidDataTypeException extends AcetateException {

    private final DataType<?> expected;
    private final DataType<?> actual;

    public InvalidDataTypeException(DataType<?> expected, DataType<?> actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public InvalidDataTypeException(DataType<?> expected, DataType<?> actual,
            String message) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    public InvalidDataTypeException(DataType<?> expected, DataType<?> actual,
            String message, Throwable cause) {
        super(message, cause);
        this.expected = expected;
        this.actual = actual;
    }

    public InvalidDataTypeException(DataType<?> expected, DataType<?> actual,
            Throwable cause) {
        super(cause);
        this.expected = expected;
        this.actual = actual;
    }

    public DataType<?> getExpected() {
        return expected;
    }

    public DataType<?> getActual() {
        return actual;
    }

}
