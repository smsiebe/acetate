package org.geoint.acetate.model.builder;

import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Defines the component context for a component map.
 *
 * @param <K> map key type
 * @param <V> map value type
 */
public interface ContextualObjectMapBuilder<K, V> {

    /**
     * Sets the (optional) object model description.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    ContextualObjectMapBuilder<K, V> description(String description);

    /**
     * Adds an attribute for this collection in this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    ContextualObjectMapBuilder<K, V> attribute(ComponentAttribute attribute);

    /**
     * Add a constraint for this collection in this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    ContextualObjectMapBuilder<K, V> constraint(ComponentConstraint constraint);

    /**
     * Return the contextual object model builder for the map key.
     *
     * @return contextual object model builder for the map key
     */
    ContextualObjectModelBuilder<K> getKeyModel();

    /**
     * Return the contextual object model builder for the map value.
     *
     * @return contextual object model builder for the map value
     */
    ContextualObjectModelBuilder<V> getValueModel();
}
