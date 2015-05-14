package org.geoint.acetate.model.constraint;

import org.geoint.acetate.model.Inheritable;
import org.geoint.acetate.model.ObjectModel;

/**
 * A constraint is a rule placed on a data (be it the model, structure, or
 * value(s)) which defines if the data is valid based on the constraint.
 *
 */
@FunctionalInterface
public interface ComponentConstraint extends Inheritable{

    /**
     * Test if the data value validates against the constraint.
     *
     * @param <T> component data type
     * @param model model of the data component
     * @param data data to test
     * @return true if the data passes the constraint, otherwise false
     */
    <T> boolean validate(ObjectModel<T> model, T data);
}
