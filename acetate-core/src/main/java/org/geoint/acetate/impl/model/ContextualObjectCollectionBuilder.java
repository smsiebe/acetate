package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ContextualCollectionModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * API to programmatically build an {@link ContextualCollectionModel}.
 *
 * @param <T> parent data type of the collection content
 */
public interface ContextualObjectCollectionBuilder<T> {

    /**
     * Sets the (optional) object model description.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    ContextualObjectCollectionBuilder<T> description(String description);

    /**
     * Adds an attribute for this collection in this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    ContextualObjectCollectionBuilder<T> attribute(ComponentAttribute attribute);

    /**
     * Add a constraint for this collection in this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    ContextualObjectCollectionBuilder<T> constraint(ComponentConstraint constraint);

    /**
     * Returns a contextual builder for the model which defines which object
     * types may be stored in the collection, allowing for context-specific
     * customization.
     *
     * @return contextual object model builder
     */
    ContextualObjectModelBuilder<T> getContainedModel();

}
