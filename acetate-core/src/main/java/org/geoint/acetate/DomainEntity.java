package org.geoint.acetate;

import java.util.Collection;

/**
 *
 * @param <T> java class representation of the domain entity
 */
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
    Collection<DomainOperation<T, ?>> getOperations();

    public static boolean isEntity(DomainType t) {
        return DomainEntity.class.isAssignableFrom(t.getClass());
    }

}
