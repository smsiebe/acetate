package org.geoint.acetate.model;

/**
 * A View is an alternate model representation.
 *
 * @param <T> data type of the modeled object
 */
public interface ViewModel<T> extends ObjectModel<T> {

    /**
     * The original model from which this view derives.
     *
     * @return model from which this view derives
     */
    ObjectModel getOriginal();

    /**
     * Domain model unique view name.
     *
     * @return view name
     */
    String getViewName();
}
