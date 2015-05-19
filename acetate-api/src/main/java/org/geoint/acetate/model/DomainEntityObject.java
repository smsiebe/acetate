package org.geoint.acetate.model;

/**
 * An Entity Object instance is a object with the domain model which is
 * identifiable by a globally unique identifier and who's specific state is
 * identifiable by an entity-unique version increment.
 *
 * Any Entity Object within a domain model may be addressed directly by its
 * unique identity (and version), from which its state and operations (defined
 * by composite and aggregate objects) may be accessed.
 *
 * @param <T>
 */
public interface DomainEntityObject<T> extends DomainObject<T> {

    /**
     * Entity objects globally unique identifier.
     *
     * An entity instance will always have the same globally unique identifier
     * throughout its life.
     * <p>
     * Implications of the entity object must ensure that the identifier is
     * <b>globally</b> unique, not just <i>domain</i> unique. How to implement
     * this globally unique identifier must be defined by the domain.
     *
     * @return the aggregate/composite domain object which is the globally
     * unique identifier of the entity
     */
    DomainObject<String> getGuid();

    /**
     * Entity object version identifier.
     *
     * The entity version specifically indicates a unique sequential state of
     * the entity instance (including the state of any composite object). As the
     * state of the entity changes the version of the entity must be
     * incremented, even if the state is manually "changed back" to a previous
     * state. The version of an entity is only ever moved forward, never
     * backward, though entity versions may be invalidated and state reverted.
     *
     * @return the aggregate/composite domain object which is the sequential
     * version of the entity
     */
    DomainObject<Long> getVersion();
}
