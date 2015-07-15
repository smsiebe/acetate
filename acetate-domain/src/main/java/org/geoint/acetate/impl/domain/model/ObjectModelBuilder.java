package org.geoint.acetate.impl.domain.model;

import java.util.Map;

/**
 * Fluid API to create an ObjectModel instance.
 *
 */
public interface ObjectModelBuilder {

    /**
     * Object identifier for the object being built.
     *
     * @return object identifier
     */
    ObjectId getObjectId();

    /**
     * Indicate that this object inherits from the specified named objects of
     * the same domain.
     *
     * @param objectNames
     * @return this builder
     */
    ObjectModelBuilder specializes(String... objectNames);

    /**
     * Indicate that this object inherits from the specified objects by object
     * identifier (allowing it to inherit from objects outside of its defined
     * domain).
     *
     * @param objectIds
     * @return this builder
     */
    ObjectModelBuilder specializes(ObjectId... objectIds);

    /**
     * Add a {@link Meta metamodel attribute} to this object model.
     *
     * @param name
     * @param value
     * @return this builder
     */
    ObjectModelBuilder withAttribute(String name, String value);

    /**
     * Add an operation to this object model.
     *
     * @param operationName
     * @return new operation builder
     */
    OperationModelBuilder withOperation(String operationName);

    /**
     * Sets all the metamodel attributes from the provided map.
     *
     * @param metamodelAttributes
     * @return this builder
     */
    ObjectModelBuilder withAttributes(Map<String, String> metamodelAttributes);

}
