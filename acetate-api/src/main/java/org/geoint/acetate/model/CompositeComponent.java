package org.geoint.acetate.model;

/**
 * A domain model component which is a member of (make up) a
 * {@link ComposedComponent }.
 *
 */
public interface CompositeComponent {

    /**
     * Contextually-unique component name.
     *
     * The local name of a component is object-unique, no other
     * CompositeComponent of a domain object may by have the same name.
     *
     * @return
     */
    String getLocalName();
}
