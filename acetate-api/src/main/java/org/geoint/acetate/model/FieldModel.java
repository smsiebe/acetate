package org.geoint.acetate.model;

/**
 * Models a single, byte-serializable, data-bearing field of the model.
 *
 * @param <T> data type of the field
 */
public interface FieldModel<T> extends ModelComponent {

    /**
     * Data type of the Field.
     *
     * @return data type
     */
    DataType<T> getDataType();

    /**
     * Optional data constraints placed on the field.
     *
     * @return any data constraints or returns empty array if no constraints
     */
    DataConstraint<T>[] getConstraints();

    /**
     * Validate the data against all the data constraints defined by this field.
     *
     * This method is for convenience and is functionally equivalent to
     * iterating over the results from {@link #getConstraints()} and calling
     * {@link DataConstraint#validate(java.lang.Object) } except throws a
     * checked exception with constraint validation problems.
     *
     * @param data data to validate
     * @throws DataConstraintException thrown if a constraint fails
     */
    void validate(T data) throws DataConstraintException;

    /**
     * Converts the provided data bytes to the field value after validating
     * against constraints.
     *
     * This method is for convenience and is functionally equivalent to
     * converting the bytes manually from the DataType returned from 
     * {@link #getDataType() } and passing that to {@link #validate(Object) }
     * except throws checked exceptions providing details of the problems.
     *
     * @param bytes data in bytes
     * @return value as a java object
     * @throws DataConstraintException thrown if the data fails a constraint
     * @throws InvalidDataTypeException thrown if the bytes do not match the
     * fields data type
     */
    T valueOf(byte[] bytes)
            throws DataConstraintException, InvalidDataTypeException;

}
