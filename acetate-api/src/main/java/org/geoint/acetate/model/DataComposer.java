package org.geoint.acetate.model;

/**
 * Composes the respective java object.
 *
 * All instances of DataComposer should be considered not thread-safe.
 *
 * @param <T> data type
 */
@FunctionalInterface
public interface DataComposer<T> {

    /**
     * Instantiate and returns the java object.
     *
     * @return java object representing the data type/value.
     * @throws DataInstantiationException thrown if there are any problems
     * instantiating the data
     */
    T compose() throws DataInstantiationException;

}
