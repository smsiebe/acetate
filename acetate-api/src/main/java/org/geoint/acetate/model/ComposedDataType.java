package org.geoint.acetate.model;

/**
 * A data type which is composed from multiple objects, such as a java object
 * which aggregates other objects.
 *
 * @param <T> java object representation of the data
 */
public interface ComposedDataType<T> extends DataType<T> {

    /**
     * The composer used to create complex data objects.
     *
     * All composers should be considered not thread-safe.
     *
     * @return data composer
     */
    DataComposer<T> getComposer();

}
