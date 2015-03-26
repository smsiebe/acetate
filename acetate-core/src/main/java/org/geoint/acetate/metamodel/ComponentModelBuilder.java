package org.geoint.acetate.metamodel;

/**
 * Common methods for building both class and field model components.
 *
 * @param <T> component data type
 */
public interface ComponentModelBuilder<T> {

    /**
     * Adds an alias for this component.
     *
     * @param alias additional alias name for this component
     * @return this builder (fluid interface)
     */
    ComponentModelBuilder<T> alias(String alias);

    /**
     * Returns the builder for the specified model aggregate, creating a new one
     * if needed.
     *
     * <b>NOTE</b>: The builder returned from this method is that of the new
     * aggregate, not this model.
     *
     * @param name local model unique component name
     * @return builder for this aggregate
     */
    ClassModelBuilder<?> aggregate(String name);

    /**
     * Returns the builder for the specified model aggregate, creating a new one
     * if needed.
     *
     * <b>NOTE</b>: The builder returned from this method is that of the new
     * aggregate, not this model.
     *
     * @param <T> aggregate type
     * @param name local model unique component name
     * @param type class type of the aggregate
     * @return builder for this aggregate
     */
    <T> ComponentModelBuilder<T> aggregate(String name, Class<T> type);

}
