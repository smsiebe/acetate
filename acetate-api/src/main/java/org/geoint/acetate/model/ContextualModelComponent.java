package org.geoint.acetate.model;

/**
 *
 * @param <B>
 */
public interface ContextualModelComponent<B> extends ContextualComponent {

    /**
     * Returns the base (non-contextual) domain model component model.
     * 
     * @return 
     */
    B getBaseModel();
}
