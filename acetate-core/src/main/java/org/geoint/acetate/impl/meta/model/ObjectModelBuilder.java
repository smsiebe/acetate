package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.MetaVersion;

/**
 * Fluid API to create an ObjectModel instance.
 *
 */
public interface ObjectModelBuilder {

    /**
     * Add a parent model to the object model.
     *
     * @param objectName
     * @return this builder
     */
    ObjectModelBuilder specializes(String objectName);

    /**
     * Add a parent model, possibly from another domain.
     *
     * @param domainId
     * @param domainVersion
     * @param objectName
     * @return this builder
     */
    ObjectModelBuilder specializes(String domainId, MetaVersion domainVersion,
            String objectName);

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

}
