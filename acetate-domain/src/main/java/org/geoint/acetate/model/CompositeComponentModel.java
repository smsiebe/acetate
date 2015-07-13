package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.constraint.ConstraintException;
import org.geoint.acetate.constraint.DataConstraint;

/**
 * A contextually-defined decorated data model component.
 *
 * @param <T>
 * @param <M>
 */
public interface CompositeComponentModel<T> {

    /**
     * Data constraints applied to the data in this context only.
     *
     * @return constraints
     */
    Collection<DataConstraint<T>> getConstraints();

    /**
     * Indicates if there may be more than one composite component instance
     * assigned to this context.
     *
     * @return true of this composite can have multiple instances of the data
     */
    boolean isMulti();

    /**
     * Validate the provided data instance against all constraints.
     *
     * @param obj
     * @throws ConstraintException
     */
    void validate(T obj) throws ConstraintException;

    /**
     * Return the data model definition from which this definition derives
     * (without context).
     *
     * @param <M>
     * @return base model without contextual information
     */
    <M extends DataModel<T>> M getBaseModel();

}
