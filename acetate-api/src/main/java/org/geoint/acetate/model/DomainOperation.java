package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 * An operation on a Entity Object which takes zero or more operation parameters
 * and returns a DomainEntityEvent on success or throws an Exception on failure.
 *
 * @param <T> return type of the operation
 */
public interface DomainOperation<T> extends DomainComponent<T>,
        CompositeComponent {

    /**
     * Domain mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    DomainEntityEvent<T, ?> getReturnModel();

    /**
     * Domain models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters; empty array if no parameters
     * are required for the operation
     */
    Collection<DomainObject<?>> getParameterModels();

}
