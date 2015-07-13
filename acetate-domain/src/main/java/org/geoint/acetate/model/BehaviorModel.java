package org.geoint.acetate.model;

import org.geoint.acetate.data.Data;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Models a behavior of an object.
 * 
 */
public interface BehaviorModel extends DomainModel {

    /**
     * Operation parameter models.
     *
     * @return parameter models
     */
    Map<String, CompositeComponentModel<?>> getParameters();

    /**
     * Operation return model.
     *
     * @return return model
     */
    Optional<? extends CompositeComponentModel<?>> getReturnType();

    /**
     * Potential error objects that can be return (thrown) if execution of the
     * operation did not successfully complete.
     *
     * @return potential exceptions that could be thrown if operation did not
     * succeed
     */
    Set<CompositeComponentModel<?>> getErrorModels();

    /**
     * Execute the operation.
     *
     * @param obj
     * @param parameters
     * @return result of the execution (success return type or error return
     * type)
     */
    Data<?> execute(Object obj, Object... parameters);
}
