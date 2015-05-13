package org.geoint.acetate.impl.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Programmatic API to define the component context.
 *
 * @param <T> component type
 */
public interface ContextBuilder<T> {

    /**
     * Set the codec to use to convert to/from an object in this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    ContextBuilder<T> codec(ObjectCodec<T> codec);

    /**
     * Adds an attribute to this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    ContextBuilder<T> attribute(ComponentAttribute attribute);

    /**
     * Add a constraint to this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    ContextBuilder<T> constraint(ComponentConstraint constraint);
}
