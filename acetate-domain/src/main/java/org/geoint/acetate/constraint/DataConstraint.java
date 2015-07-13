package org.geoint.acetate.constraint;

import org.geoint.acetate.data.Data;

/**
 * A constraint is a rule which data can be validated against.
 * 
 * <p>
 * All implementations of DataConstraint must be thread-safe.
 * 
 * @param <T> java data type this constraint can test
 */
@FunctionalInterface
public interface DataConstraint<T> {

    /**
     * Validate the object against this constraint.
     * 
     * @param obj data to test
     * @throws ConstraintException thrown if the data failed validation or 
     * validation could not be conducted
     */
   void validate (Data<T> obj) throws ConstraintException;
}
