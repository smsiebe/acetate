package org.geoint.acetate.meta.model;

import java.util.Set;

/**
 * Models what may be returned from the execution of an {@link OperationModel
 * operation}.
 *
 */
public interface ReturnModel {

    /**
     * Model of the returned value upon successful execution of the operation.
     *
     * @return return value upon successful execution of the operation
     */
    ObjectModel getModel();

    /**
     * Potential exceptions that can be thrown if execution of the operation did
     * not successfully complete.
     *
     * @return potential exceptions that could be thrown if operation did not
     * succeed
     */
    Set<ThrowableModel> getExceptions();
}
