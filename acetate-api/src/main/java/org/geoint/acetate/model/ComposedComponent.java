package org.geoint.acetate.model;

/**
 * A domain model component that is composed from other domain model components.
 */
public interface ComposedComponent {

    /**
     * The object model of the composite which contains this component.
     *
     * @return object model which contains this component
     */
    DomainObject<?> getComposite();
}
