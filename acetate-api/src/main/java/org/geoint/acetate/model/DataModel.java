package org.geoint.acetate.model;

import org.geoint.acetate.bound.BoundData;

/**
 * Model of a data structure.
 */
public interface DataModel {

    /**
     * Returns the "root" component of the data model.
     *
     * @return model "root" component
     */
    ComponentModel getRoot();

    /**
     * Structural constraints placed on the model.
     *
     * @return model constraints
     */
    ComponentConstraint[] getConstraints();

    /**
     * Validates <i>both</i> both the model and data against the model.
     *
     * This method must be functionally equivalent to calling:
     *
     * {@code
     *   BoundData bound = ...
     *   {
     *     bound.validateData();
     *     bound.validateModel();
     *   }
     * }
     *
     * @param bound
     * @throws ModelConstraintException
     */
    void validate(BoundData bound) throws ModelConstraintException;

}
