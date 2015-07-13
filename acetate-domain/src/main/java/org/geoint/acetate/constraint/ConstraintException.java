package org.geoint.acetate.constraint;

/**
 *
 */
public class ConstraintException extends Exception {

    private final Class<?> dataType;

    public ConstraintException(Class<?> dataType) {
        this.dataType = dataType;
    }

    public ConstraintException(Class<?> dataType, String message) {
        super(message);
        this.dataType = dataType;
    }

    public ConstraintException(Class<?> dataType, String message, Throwable cause) {
        super(message, cause);
        this.dataType = dataType;
    }

    public ConstraintException(Class<?> dataType, Throwable cause) {
        super(cause);
        this.dataType = dataType;
    }

    public Class<?> getDataType() {
        return dataType;
    }

}
