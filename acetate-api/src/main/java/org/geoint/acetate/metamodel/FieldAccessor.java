package org.geoint.acetate.metamodel;

/**
 * Retrieves the value of a field.
 *
 * @param <P> containing object type
 * @param <F> field value type
 */
@FunctionalInterface
public interface FieldAccessor<P, F> {

    /**
     * Return the value of the field.
     *
     * @param data data to retrieve the field data from
     * @return the field value or null
     */
    F get(P data);
}
