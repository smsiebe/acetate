package org.geoint.acetate.domain.entity;

import java.util.Collection;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.domain.operation.DomainOperation;

/**
 *
 * @param <T> java class representation of the domain entity
 */
@Entity(name="DomainEntity")
public interface DomainEntity<T> extends DomainType<T> {

    /**
     * Globally unique domain entity ID.
     *
     * @param entity entity instance
     * @return domain entity instance id
     */
    String getEntityId(T entity);

    /**
     * Entity version.
     *
     * @param entity entity instance
     * @return entity version
     */
    String getEntityVersion(T entity);

    /**
     * Domain operations (behavior) defined for the Entity.
     *
     * @return entity operations
     */
    Collection<DomainOperation<T>> getOperations();

}
