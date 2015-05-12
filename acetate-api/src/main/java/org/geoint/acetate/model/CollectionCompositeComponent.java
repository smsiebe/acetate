package org.geoint.acetate.model;

/**
 * A composite component which may contain multiple instances of a component
 * model.
 *
 * @param <T> data type of the components contained in the collection
 */
public interface CollectionCompositeComponent<T> extends CompositeComponent {

    /**
     * Component model which defines the component context of the composite
     * collection.
     *
     * @return model of the composite collection
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
