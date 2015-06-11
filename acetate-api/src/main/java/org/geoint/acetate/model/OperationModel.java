package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 * An operation on a Entity Object which takes zero or more operation parameters
 * and returns a DomainEntityEvent on success or throws an Exception on failure.
 *
 * @param <R> return type of the operation
 */
public interface OperationModel<R> extends Composable<R> {

    /**
     * The name of the operation, which must be unique to the container the
     * operation is defined.
     *
     * @return operation name
     */
    String getOperationName();

    /**
     * Domain mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    DomainEntityEvent<R, ?> getReturnModel();

    /**
     * Domain models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters in declaration order; empty
     * collection if no parameters are required for the operation
     */
    Collection<ObjectModel<?>> getParameterModels();

}
