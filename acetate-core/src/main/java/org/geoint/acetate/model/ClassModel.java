package org.geoint.acetate.model;

import java.util.Collection;

/**
 * Models a Class which contains (aggregates) additional DataModels.
 *
 * @param <T> corresponding java class type
 */
public interface ClassModel<T> extends DataModel<T> {

    /**
     * Return all components of this class.
     *
     * @return all components of the class or an empty collection
     */
    Collection<DataModel<?>> getComponents();

}
