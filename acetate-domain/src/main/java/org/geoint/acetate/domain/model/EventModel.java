package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.model.CompositeModel;

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
