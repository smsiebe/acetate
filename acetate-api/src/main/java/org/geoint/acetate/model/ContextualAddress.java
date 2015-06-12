package org.geoint.acetate.model;

/**
 * Address for a component which is contained within another model component.
 */
public interface ContextualAddress extends ComponentAddress {

    /**
     * Contextual component of the address.
     *
     * @return contextual address
     */
    String getContext();
}
