package org.geoint.acetate.model;

/**
 * A domain model component which can be a member (make up) of a 
 * {@link ComposedComponent }.
 *
 */
public interface ComposableComponent {

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
