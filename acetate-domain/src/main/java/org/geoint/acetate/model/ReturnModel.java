package org.geoint.acetate.model;

import java.util.Set;
import org.geoint.acetate.meta.annotation.Model;

/**
 * Contextual model of the return type of an Operation.
 */
@Model(name="return", domainName="acetate", domainVersion=1)
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
