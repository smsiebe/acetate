package org.geoint.acetate.domain;

import java.util.Collection;

/**
 *
 * @param <T> java class representation of the domain entity
 */
public interface DomainEntity<T> extends DomainType<T> {

    /**
     * Globally unique domain entity ID.
     *
     * @return domain entity instance id
     */
    String getEntityId();

    /**
     * Entity version.
     *
     * @return entity version
     */
    String getEntityVersion();

    /**
     * Domain operations (behavior) defined for the Entity.
     *
     * @return entity operations
     */
    Collection<DomainEvent> getOperations();

}
