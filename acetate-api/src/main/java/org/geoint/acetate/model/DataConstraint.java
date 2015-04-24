package org.geoint.acetate.model;

/**
 * A constraint is a rule placed on a data (be it the model, structure, or
 * value(s)) which defines if the data is valid based on the constraint.
 *
 * @param <T> java object type supported by this constraint
 */
@FunctionalInterface
public interface DataConstraint<T> {

    /**
     * Test if the data value validates against the constraint.
     *
     * @param model model of the data component
     * @param data data to test
     * @return true if the data passes the constraint, otherwise false
     */
    boolean validate(DataComponent<T> model, T data);
}
