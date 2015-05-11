package org.geoint.acetate.model;

/**
 * A composite component which may contain multiple instances of a component
 * model.
 *
 * @param <T> data type of the components contained in the collection
 */
public interface CollectionCompositeComponent<T>
        extends CompositeComponent {

    /**
     * Component model which defines the component context of the composite
     * collection.
     *
     * @return model of the composite collection
     */
    ComponentModel<T> getComponentModel();

}
