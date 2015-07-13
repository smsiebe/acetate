package org.geoint.acetate.entity.model;

import org.geoint.acetate.model.CompositeModel;

/**
 * Models a special object type that records changes to an Entity instance.
 *
 * @param <T>
 */
public interface EventModel<T> extends CompositeModel<T> {

    String getEntityId(T obj);

    String getAppliedEntityVersion(T obj);

    String getNewEntityVersion(T obj);
}
