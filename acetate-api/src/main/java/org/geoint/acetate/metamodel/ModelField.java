package org.geoint.acetate.metamodel;

/**
 * Data field leaf node which provides access to an (optional) value.
 *
 * @param <C> container object type
 * @param <T> the value type
 */
public interface ModelField<C, T> extends ModelComponent<T> {

    /**
     * Accessor to the component value.
     *
     * @return value accessor
     */
    FieldAccessor<C, T> getAccessor();

    /**
     * Setter to change the component value
     *
     * @return value setter
     */
    FieldSetter<C, T> getSetter();
}
