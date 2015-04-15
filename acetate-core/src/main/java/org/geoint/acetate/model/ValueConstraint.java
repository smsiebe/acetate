package org.geoint.acetate.model;

/**
 * A constraint on a data value.
 *
 * @param <T> data type for which this constraint applies
 */
@FunctionalInterface
public interface ValueConstraint<T> {

    /**
     * Test if the data value validates against the constraint.
     *
     * @param model value model
     * @param data data to test
     * @return true if the data passes the constraint, otherwise false
     */
    boolean validate(ValueModel<T> model, T data);

}
