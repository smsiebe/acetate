package org.geoint.acetate.model;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * A behavior/operation of domain model component.
 *
 */
public interface ComponentOperation {

    /**
     * Name of the operation.
     *
     * @return operation name
     */
    String getOperationName();

    /**
     * Optional operation description.
     *
     * @return optional operation description
     */
    Optional<String> getDescription();

    /**
     * Optional method that would be called on the model object.
     *
     * If the data model is not bound to a java type, no Method reference will
     * be available.
     *
     * @return operation method
     */
    Optional<Method> getMethod();

    /**
     * Domain mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    Optional<ComponentModel<?>> getReturnModel();

    /**
     * Domain models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters; empty array if no parameters
     * are required for the operation
     */
    ComponentModel<?>[] getParameterModels();
}
