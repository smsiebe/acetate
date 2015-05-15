package org.geoint.acetate.impl.model;

import org.geoint.acetate.data.transform.ObjectCodec;
import org.geoint.acetate.model.ContextualObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Programmatic API to define a {@link ContextualObjectModel}.
 *
 * @param <T> component type
 */
public interface ContextualObjectModelBuilder<T> {

    /**
     * Sets the (optional) object model description.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> description(String description);

    /**
     * Adds an attribute for this collection in this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> attribute(ComponentAttribute attribute);

    /**
     * Add a constraint for this collection in this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> constraint(ComponentConstraint constraint);

    /**
     * Set the codec to use to convert to/from an object in this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> codec(ObjectCodec<T> codec);

    /**
     * Ignores a component operation defined by the base domain model object.
     *
     * @param operationName operation name
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> ignoreOperation(String operationName);

    /**
     * Ignore a composite component from the base domain model object.
     *
     * @param compositeName component-unique composite name
     * @return this builder (fluid interface)
     */
    ContextualObjectModelBuilder<T> ignoreComposite(String compositeName);

}
