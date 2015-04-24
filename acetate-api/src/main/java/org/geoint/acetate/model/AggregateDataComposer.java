package org.geoint.acetate.model;

/**
 * Composes a Java object instance from aggregate objects.
 *
 * All instances of AggregateDataComposer should be considered not thread-safe.
 *
 * @param <T> data type
 */
public interface AggregateDataComposer<T> extends DataComposer<T> {

    /**
     * Add an aggregate object.
     *
     * @param fieldName model-defined name of the field
     * @param value field value
     * @throws InvalidDataTypeException thrown if the value type provided is
     * invalid for this field
     */
    void aggregate(String fieldName, Object value)
            throws InvalidDataTypeException;

    /**
     * Add an aggregate object.
     *
     * @param fieldName model-defined name of the field
     * @param composer composer method reference
     */
    void aggregate(String fieldName, DataComposer<?> composer);
}
