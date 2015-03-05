package org.geoint.acetate.metamodel;

/**
 * Mutator of a fields data.
 *
 * @param <P> containing object type
 * @param <F> the field value type
 */
@FunctionalInterface
public interface FieldSetter<P, F> {

    /**
     * Set the value of the object.
     *
     * @param dataItem data item to set the value
     * @param value field value
     * @return true if the value was set, otherwise false
     */
    boolean set(P dataItem, F value);
}
