package org.geoint.acetate.metamodel;

/**
 * Retrieves the value of a field.
 *
 * @param <D> data object to retrieve the data from
 * @param <F> field value
 */
@FunctionalInterface
public interface FieldAccessor<D, F> {

    /**
     * Return the value of the field.
     *
     * @param data data to retrieve the field data from
     * @return the field value or null
     */
    F get(D data);
}
