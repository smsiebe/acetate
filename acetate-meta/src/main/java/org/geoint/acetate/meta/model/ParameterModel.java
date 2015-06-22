package org.geoint.acetate.meta.model;

/**
 * Model of an Operation parameter.
 *
 * @param <T>
 */
public interface ParameterModel<T> {

    String getName();

    ObjectModel<T> getModel();

}
