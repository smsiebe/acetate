package org.geoint.acetate.model.builder;

import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 */
public interface OperationModelBuilder {

    /**
     * Sets the (optional) operation description.
     *
     * @param description description of the operation
     * @return this builder (fluid interface)
     */
    OperationModelBuilder description(String description);

    /**
     * Adds an attribute to this context.
     *
     * @param attribute
     * @return this builder (fluid interface)
     */
    OperationModelBuilder attribute(ComponentAttribute attribute);

    /**
     * Add a constraint to this context.
     *
     * @param constraint
     * @return this builder (fluid interface)
     */
    OperationModelBuilder constraint(ComponentConstraint constraint);

    ContextualObjectModelBuilder<?> returns(String objectName);

    ContextualObjectCollectionBuilder<?> returnsCollection(String objectName);

    ContextualObjectMapBuilder<?, ?> returnsMap(String keyObjectName,
            String valueObjectName);

    ContextualObjectModelBuilder<?> parameter(String paramName, String objectName);

    ContextualObjectCollectionBuilder<?> collectionParameter(String paramName,
            String objectName);

    ContextualObjectMapBuilder<?, ?> maparameter(String paramName,
            String keyObjectName, String valueObjectName);

}
