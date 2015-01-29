package org.geoint.acetate.metamodel;

/**
 * Mutator of a fields data.
 *
 * @param <D> data item to set the value
 * @param <F> the field value type
 */
@FunctionalInterface
public interface FieldSetter<D, F> {

    /**
     * Set the value of the object.
     *
     * @param dataItem data item to set the value
     * @param value field value
     * @return true if the value was set, otherwise false
     */
    boolean set(D dataItem, F value);
}
