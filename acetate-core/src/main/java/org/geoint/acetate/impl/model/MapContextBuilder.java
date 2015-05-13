package org.geoint.acetate.impl.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Defines the component context for a component map.
 *
 * @param <K> map key type
 * @param <V> map value type
 */
public interface MapContextBuilder<K, V> {

    /**
     * Set the codec to use to convert to/from an object in this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> keyCodec(ObjectCodec<K> codec);

    /**
     * Adds an attribute to this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> keyAttribute(ComponentAttribute attribute);

    /**
     * Add a constraint to this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> keyConstraint(ComponentConstraint constraint);

    /**
     * Set the codec to use to convert to/from an object in this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> valueCodec(ObjectCodec<V> codec);

    /**
     * Adds an attribute to this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> valueAttribute(ComponentAttribute attribute);

    /**
     * Add a constraint to this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    MapContextBuilder<K, V> valueConstraint(ComponentConstraint constraint);
}
