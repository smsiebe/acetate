package org.geoint.acetate.model;

/**
 * A (possibly altered) component model bound to an arbitrary context.
 *
 * When corresponding data of this model is encountered in within this context,
 * the contextual model is used instead of the base domain model.
 *
 * @param <M>
 */
public interface ContextualComponentModel<M extends ModelComponent>
        extends ModelComponent {

    @Override
    ContextualAddress getAddress();

    /**
     * The original model from which this view derives.
     *
     * @return model from which this view derives
     */
    M getBaseModel();

}
