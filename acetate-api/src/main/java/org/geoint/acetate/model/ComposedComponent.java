package org.geoint.acetate.model;

/**
 * A domain model component which may be composed from zero or more
 * {@link CompositeComponent composite components}.
 */
public interface ComposedComponent {

    /**
     * The object model of the composite which contains this component.
     *
     * @return object model which contains this component
     */
    DomainObject<?> getComposite();
}
