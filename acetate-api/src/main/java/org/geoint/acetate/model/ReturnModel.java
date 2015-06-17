package org.geoint.acetate.model;

import java.util.Set;
import org.geoint.acetate.model.annotation.Domain;

/**
 * Contextual model of the return type of an Operation.
 */
@Domain(name = "acetate", version = 1)
public interface ReturnModel extends ContextualModel {

    /**
     * Model for the event returned on successful execution of the operation.
     *
     * @return event model returned on success
     */
    EventModel<?> getSuccessModel();

    /**
     * Models for all the possible exception types of the operation.
     *
     * @return exception models thrown on failure
     */
    Set<ExceptionModel<?>> getFailureModels();
}
