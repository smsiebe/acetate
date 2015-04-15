package org.geoint.acetate.model;

import java.util.Collection;

/**
 * A component of the DataModel.
 *
 * @param <T> java class type this meta model component represents
 *
 * @see DataModel
 * @see ValueModel
 * @see ClassModel
 */
public interface DataModel<T> {

    /**
     * Optional data constraints placed on the field.
     *
     * @return any data constraints or returns empty collection if no
     * constraints
     */
    Collection<ValueConstraint<T>> getConstraints();

    /**
     * Validate the data against all the data constraints defined by this field.
     *
     * This method is for convenience and is functionally equivalent to
     * iterating over the results from {@link #getConstraints()} and calling
     * {@link DataConstraint#validate(java.lang.Object) } except throws a
     * checked exception with constraint validation problems.
     *
     * @param data data to validate
     * @throws ValueConstraintException thrown if a constraint fails
     */
    void validate(T data) throws ValueConstraintException;

}
