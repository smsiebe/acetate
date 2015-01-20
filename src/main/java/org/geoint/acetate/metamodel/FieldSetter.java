package org.geoint.acetate.metamodel;

/**
 * Mutator of a fields data.
 *
 * @param <F> the field value type
 */
@FunctionalInterface
public interface FieldSetter<F> {

    /**
     * Set the value of the object.
     *
     * @param value field value
     * @return true if the value was set, otherwise false
     */
    boolean set(F value);
}
