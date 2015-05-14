package org.geoint.acetate.model;

/**
 *
 * @param <B>
 */
public interface ContextualModelComponent<B> extends ContextualComponent {

    B getBaseModel();
}
