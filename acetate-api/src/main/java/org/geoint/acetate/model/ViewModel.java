package org.geoint.acetate.model;

/**
 * A View is a contextually bound alternative object model representation.
 *
 * @param <T> data type of the modeled object
 */
public interface ViewModel<T> extends ContextualComponentModel<ObjectModel<T>>,
        ObjectModel<T> {

}
