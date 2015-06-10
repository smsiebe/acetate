package org.geoint.acetate.model;

/**
 * A domain model component which is a member of (make up) a
 * {@link ComposedComponent }.
 *
 * @param <T> data type of the component
 */
public interface CompositeComponent<T> extends DomainComponent<T> {

    /**
     * Contextually-unique component name.
     *
     * The local name of a component is object-unique, no other
     * CompositeComponent of a domain object may by have the same name.
     *
     * @return contextually-unique composite component name
     */
    String getLocalName();

    /**
     * Return the object model of the component which declared this component.
     *
     * @return object model of the component which declared this composite
     * component
     */
    DomainObject<?> getDeclaringComponent();

}
