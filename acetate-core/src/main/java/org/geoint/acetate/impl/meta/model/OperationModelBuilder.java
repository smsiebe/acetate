package org.geoint.acetate.impl.meta.model;

/**
 * Fluid API to create an OperationModel.
 *
 */
public interface OperationModelBuilder {

    /**
     * Sets the optional operation description.
     *
     * @param desc optional description, may be null
     * @return this builder
     */
    OperationModelBuilder withDescription(String desc);

    /**
     * Add a parameter to the operation.
     *
     * @param paramName
     * @param paramModel
     * @return this builder
     * @throws DuplicateParametersException thrown if this parameter name is
     * already in use for this operation
     */
    OperationModelBuilder withParameter(String paramName, DomainId paramModel)
            throws DuplicateParametersException;

    /**
     * Specify the return type of the operation.
     *
     * @param returnModel
     * @return this builder
     */
    OperationModelBuilder withReturn(DomainId returnModel);

    /**
     * Indicate the return type is <i>void</i>.
     *
     * By default, void is the return type of the OperationModelBuilder
     * instance.
     *
     * @return this builder
     */
    OperationModelBuilder voidReturn();

    /**
     * Add an exception type this operation can throw.
     *
     * @param exceptionModel
     * @return this builder
     */
    OperationModelBuilder withException(DomainId exceptionModel);

}
