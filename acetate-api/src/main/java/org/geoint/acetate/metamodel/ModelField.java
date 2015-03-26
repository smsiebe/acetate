package org.geoint.acetate.metamodel;

/**
 * Data field leaf node which provides access to an (optional) value.
 *
 * @param <P> parent container object type
 * @param <F> the field value type
 */
public interface ModelField<P, F> extends ModelComponent<F> {

    /**
     * Accessor to the field data value.
     *
     * @return value accessor
     */
    FieldAccessor<P, F> getAccessor();

    /**
     * Setter to change the field data value.
     *
     * @return value setter
     */
    FieldSetter<P, F> getSetter();
}
