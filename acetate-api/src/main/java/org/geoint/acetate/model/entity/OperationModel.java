package org.geoint.acetate.model.entity;

import java.util.Collection;
import org.geoint.acetate.model.ComposableModelComponent;
import org.geoint.acetate.model.ObjectModel;

/**
 * An operation is composed of parameter and return models which represent a a
 * behavior of an Entity.
 *
 * @param <R> return type of the operation
 */
public interface OperationModel<R extends EventModel>
        extends ComposableModelComponent {

    /**
     * Domain mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    EventModel<?> getReturnModel();

    /**
     * Domain models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters in declaration order; empty
     * collection if no parameters are required for the operation
     */
    Collection<ObjectModel<?>> getParameterModels();

}
