package org.geoint.acetate.model;

/**
 * Constraint on a data value.
 *
 * @param <T> data type for which this constraint applies
 */
@FunctionalInterface
public interface DataConstraint<T> {

    /**
     * Test if the data validates against the constraint.
     *
     * @param data data to test
     * @return true if the data passes the constraint, otherwise false
     */
    boolean validate(T data);

}
