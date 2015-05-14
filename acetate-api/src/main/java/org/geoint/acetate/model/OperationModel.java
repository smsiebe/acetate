package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.attribute.Attributable;

/**
 * A behavior/operation of domain model component.
 *
 */
public interface OperationModel extends ModelComponent,
        Attributable, Inheritable {

    /**
     * Domain mode of the returned type.
     *
     * @return model of the returned type from the method or null if returns
     * void
     */
    Optional<ContextualComponent> getReturnModel();

    /**
     * Domain models of the operation parameters, in declaration order.
     *
     * @return models of the operation parameters; empty array if no parameters
     * are required for the operation
     */
    Collection<? extends ContextualComponent> getParameterModels();

}
