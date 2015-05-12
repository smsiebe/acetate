package org.geoint.acetate.model;

/**
 * A composite component which may only be a single domain model component
 * instance.
 *
 * @param <T> data type of the component model
 */
public interface SimpleCompositeComponent<T> extends CompositeComponent {

    /**
     * Model of the component which may be used in this composite context.
     *
     * @return component model for this composite context
     */
    ComponentModel<T> getComponentModel();

    /**
     * The context of the component defined by the composite relationship.
     *
     * The context returned by this method is the "resultant" context which
     * should be used for the component in this context, rather than the default
     * context returned from the ComponentModel.
     *
     * @return component context used within context of this composite
     */
    ComponentContext<T> getCompositeContext();
}
