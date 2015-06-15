package org.geoint.acetate.model.entity;

import org.geoint.acetate.model.ComposableModelComponent;

/**
 * A domain entity object which is used in the composition of another entity,
 * forming a relationship between the entities.
 *
 * @param <T> java data type of the aggregated object
 */
public interface AggregateModel<T> extends EntityModel<T>,
        ComposableModelComponent<EntityModel<T>> {

}
