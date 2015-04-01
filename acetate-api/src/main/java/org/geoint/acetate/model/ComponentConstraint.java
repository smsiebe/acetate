package org.geoint.acetate.model;

import org.geoint.acetate.bound.BoundData;

/**
 * Constraint placed on the data model itself verifying that bound data complies
 * with any model (schema) constraints.
 */
public interface ComponentConstraint {

    /**
     * Verify the bound data complies with the constraint.
     *
     *
     * @param bound bound data
     * @return true if the bound data complies with the constraint
     */
    boolean validate(BoundData bound);
}
