package org.geoint.acetate.model;

/**
 * A constraint placed on the data model.
 *
 * @param <T> data model component type
 */
@FunctionalInterface
public interface DataModelConstraint<T> {

    /**
     * Test if the data and model validate against this constraint.
     *
     * @param model data model
     * @param data data
     * @return true if the data/model passes, otherwise false
     */
    boolean validate(DataModel<T> model, T data);

}
