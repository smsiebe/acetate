package org.geoint.acetate.model;

/**
 * Model of an object defined contextually as a composite.
 *
 * @param <T>
 */
public interface CompositeObjectModel<T> extends ObjectModel<T>,
        CompositeComponentModel<T> {

    @Override
    public ObjectModel<T> getBaseModel();
}
