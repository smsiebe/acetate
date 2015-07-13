package org.geoint.acetate.model;

/**
 * Composite value data object model.
 * 
 * @param <T>
 */
public interface CompositeValueModel<T> extends ValueModel<T>,
        CompositeComponentModel<T> {

    @Override
    public ValueModel<T> getBaseModel();

}
