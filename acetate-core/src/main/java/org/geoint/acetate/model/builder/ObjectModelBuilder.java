package org.geoint.acetate.model.builder;

import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.model.DomainObjectOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * API to programmatically build an {@link DomainObject}.
 *
 * @param <T> component data type
 */
public interface ObjectModelBuilder<T> {

    /**
     * Sets the (optional) object model description.
     *
     * @param description description of the object model
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> description(String description);

    /**
     * Indicates that the component inherits from the provided component name.
     *
     * @param parentComponentName domain-unique name of component from which
     * this component inherits
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> specializes(String parentComponentName);

    /**
     * Add a component operation on the domain model.
     *
     * @param operation component operation
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> operation(DomainObjectOperation operation);

    /**
     * Explicitly sets the codec to use to convert to/from an object in this
     * context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> codec(ComplexObjectCodec<T> codec);

    /**
     * Adds an attribute to this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> attribute(ComponentAttribute attribute);

    /**
     * Add a constraint to this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    ObjectModelBuilder<T> constraint(ComponentConstraint constraint);

    /**
     * Add a composite component to this component.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name for this composite type
     * @return composite context builder
     */
    ContextualObjectModelBuilder<?> composite(String localName, String componentName);

    /**
     * Add a multi-valued composite collection.
     *
     * @param localName component-unique composite name
     * @param componentName domain-unique component name of the acceptable
     * component types in the collection
     * @return composite context builder
     */
    ContextualObjectModelBuilder<?> compositeCollection(String localName,
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
    ContextualObjectMapBuilder<?, ?> compositeMap(String localName,
            String keyComponentName, String valueComponentName);

}
