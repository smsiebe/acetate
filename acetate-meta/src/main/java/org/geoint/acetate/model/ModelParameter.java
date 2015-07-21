package org.geoint.acetate.model;

/**
 * Model method parameter.
 *
 * @param <T>
 */
public interface ModelParameter<T> extends ModelTypeUse<T> {

    /**
     * Name of the method parameter.
     *
     * @return method parameter name
     */
    String getParameterName();

}
