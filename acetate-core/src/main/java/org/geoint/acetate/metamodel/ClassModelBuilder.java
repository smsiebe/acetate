package org.geoint.acetate.metamodel;

/**
 * Builder for adding a new class/aggregate to the model.
 *
 * @param <T> data type of the class
 */
public interface ClassModelBuilder<T> extends ComponentModelBuilder<T> {

    /**
     * Returns the builder for the specified model field which is a component of
     * this class, creating a new one if needed.
     *
     * @param name name of the field (do not use alias)
     * @return builder for the field
     */
    FieldModelBuilder<T, ?> field(String name);

    /**
     * Returns the builder for the specified model field which is a component of
     * this class, creating a new one if needed.
     *
     * @param <F> field data type
     * @param name name of the field (do not use alias)
     * @param fieldType field data type
     * @return builder for the field
     */
    <F> FieldModelBuilder<T, F> field(String name, Class<F> fieldType);

}
