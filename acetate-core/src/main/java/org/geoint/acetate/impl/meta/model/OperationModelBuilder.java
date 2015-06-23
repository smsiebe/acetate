package org.geoint.acetate.impl.meta.model;

/**
 * Fluid API to create an OperationModel.
 *
 * @param <R> operation return type
 */
public interface OperationModelBuilder<R> {

    /**
     * Add a parameter to the operation.
     *
     * @param param
     * @param model
     * @return this builder
     * @throws DuplicateParametersException thrown if this parameter name is
     * already in use for this operation
     */
    OperationModelBuilder<R> withParameter(String param, Class<?> model)
            throws DuplicateParametersException;

    /**
     * Specify the return type of the operation.
     *
     * @param model
     * @return this builder
     */
    OperationModelBuilder<R> withReturn(Class<?> model);

    /**
     * Indicate the return type is <i>void</i>.
     *
     * By default, void is the return type of the OperationModelBuilder
     * instance.
     *
     * @return this builder
     */
    OperationModelBuilder<R> voidReturn();

    /**
     * Add a Throwable type this operation can throw.
     *
     * @param throwableType
     * @return this builder
     */
    OperationModelBuilder<R> withException(Class<? extends Throwable> throwableType);

}
