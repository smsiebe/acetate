package org.geoint.acetate.model;

/**
 * A domain model component may be used in the definition of another component
 * {@link ModelComponent component }.
 *
 */
public interface ComposableModelComponent extends ModelComponent {

    /**
     * Indicates if the composite is singular or there may be more than one
     * instance of the composite.
     *
     * @return true if the aggregate accepts multiple entity instances
     */
    boolean isCollection();
}
