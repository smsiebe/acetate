package org.geoint.acetate.model;

/**
 * A domain model component which is a component of another domain model object.
 *
 * @see DomainAggregateObject
 * @see DomainCompositeObject
 * @
 */
public interface ComposableComponent extends Inheritable {

    /**
     * The object model of the composite which contains this component.
     *
     * @return object model which contains this component
     */
    DomainObject<?> getComposite();

    /**
     * Contextually-unique component name.
     *
     * The local name of a component is object-unique, no other
     * ComposableComponent of a domain object may by have the same name.
     *
     * @return
     */
    String getLocalName();
}
