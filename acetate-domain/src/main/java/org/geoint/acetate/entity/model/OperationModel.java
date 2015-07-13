package org.geoint.acetate.entity.model;

import java.util.Optional;
import org.geoint.acetate.model.BehaviorModel;

/**
 * Specialized behavior of an Entity which, on successful execution, changes the
 * entity and returns an Event detailing the change.
 * <p>
 * If the entity operation fails for any reason, no change to the Entity state
 * will take place, and the object returned from this operation is informative
 * about the condition of the error (is not an event applied to the entity).
 */
public interface OperationModel extends BehaviorModel {

    @Override
    Optional<CompositeEventModel<?>> getReturnType();
}
