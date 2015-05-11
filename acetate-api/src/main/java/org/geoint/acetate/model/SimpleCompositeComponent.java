package org.geoint.acetate.model;

/**
 * A composite component which may only be a single domain model component
 * instance.
 *
 * @param <T> data type of the component model
 */
public interface SimpleCompositeComponent<T> extends CompositeComponentModel {

    /**
     * Model of the component which may be used in this composite context.
     *
     * @return component model for this composite context
     */
    ComponentModel<T> getComponentModel();
}
