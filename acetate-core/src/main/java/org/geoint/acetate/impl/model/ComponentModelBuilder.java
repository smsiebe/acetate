package org.geoint.acetate.impl.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * API to programmatically build a ComponentModel.
 *
 * @param <T> component data type
 */
public interface ComponentModelBuilder<T> {

    /**
     * Add a constraint to this component model.
     *
     * @param constraint constraint to be added to this model
     * @return this builder (fluid interface)
     */
    ComponentModelBuilder constraint(ComponentConstraint constraint);

    /**
     * Add an attribute to this component model.
     *
     * @param attribute attribute for this component model
     * @return this builder (fluid interface)
     */
    ComponentModelBuilder attribute(ComponentAttribute attribute);

    /**
     * Set an explicit codec to use for this component.
     *
     * @param codec codec to use for this component
     * @return this builder (fluid interface)
     */
    ComponentModelBuilder<T> codec(ObjectCodec<T> codec);

    /**
     * Add a composite component to this component.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name for this composite type
     * @return builder for new composite component
     */
    ComponentModelBuilder composite(String localName, String componentName);

    /**
     * Add a multi-valued composite collection.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name of the acceptable
     * component types in the collection
     * @return builder for new composite component
     */
    ComponentModelBuilder compositeCollection(String localName,
            String componentName);

    /**
     * Add a mapped composite collection.
     *
     * @param localName component-unique composite name
     * @param keyComponentName domain-unique component name used as the key of
     * the map
     * @param valueComponentName domain-unique component name used as the value
     * of the map
     * @return builder for the new composite map component
     */
    ComponentModelBuilder compositeMap(String localName,
            String keyComponentName, String valueComponentName);

}
