package org.geoint.acetate.impl.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * API to programmatically build a ComponentModel.
 *
 * @param <T> component data type
 */
public interface ComponentModelBuilder<T> extends ContextBuilder<T> {

    /**
     * Indicates that the component inherits from the provided component name.
     *
     * @param parentComponentName domain-unique name of component from which
     * this component inherits
     * @return this builder (fluid interface)
     */
    ComponentModelBuilder<T> specializes(String parentComponentName);

    /**
     * Add a component operation on the domain model.
     *
     * @param name operation name
     * @return builder for the component operation
     */
    ComponentOperationBuilder operation(String name);

    /**
     * Add a composite component to this component.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name for this composite type
     * @return composite context builder
     */
    ContextBuilder<?> composite(String localName, String componentName);

    /**
     * Add a multi-valued composite collection.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name of the acceptable
     * component types in the collection
     * @return composite context builder
     */
    ContextBuilder<?> compositeCollection(String localName,
            String componentName);

    /**
     * Add a mapped composite collection.
     *
     * @param localName component-unique composite name
     * @param keyComponentName domain-unique component name used as the key of
     * the map
     * @param valueComponentName domain-unique component name used as the value
     * of the map
     * @return composite context builder
     */
    MapContextBuilder<?, ?> compositeMap(String localName,
            String keyComponentName, String valueComponentName);

}
