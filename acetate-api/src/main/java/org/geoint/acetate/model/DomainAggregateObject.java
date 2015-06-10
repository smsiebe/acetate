package org.geoint.acetate.model;

/**
 * A domain entity object which is related to another object.
 *
 * @param <T> type of the aggregated object
 */
public interface DomainAggregateObject<T> extends DomainEntityObject<T>,
        CompositeComponent<T> {

    /**
     * Indicates if there can be more than one aggregate instance related (a
     * collection).
     *
     * @return true if the aggregate accepts multiple entity instances
     */
    boolean isCollection();
}
