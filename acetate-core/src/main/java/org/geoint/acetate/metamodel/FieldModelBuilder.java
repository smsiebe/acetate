package org.geoint.acetate.metamodel;

/**
 * Builder for adding a new field to the model.
 *
 * @param <P> data type of the component that contains this field
 * @param <F> data type of the field
 */
public interface FieldModelBuilder<P, F>
        extends ComponentModelBuilder<F> {

    /**
     * Accessor to retrieve the field data value.
     *
     * @param accessor accessor to retrieve the data value
     * @return this builder
     */
    FieldModelBuilder<P, F> accessor(FieldAccessor<P, F> accessor);

    /**
     * Setter to mutate the field data value.
     *
     * @param setter setter to mutate the data value
     * @return this builder
     */
    FieldModelBuilder<P, F> setter(FieldSetter<P, F> setter);

}
