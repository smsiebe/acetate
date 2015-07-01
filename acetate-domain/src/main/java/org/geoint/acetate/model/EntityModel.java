package org.geoint.acetate.model;

import org.geoint.acetate.meta.annotation.Model;

/**
 * An Entity special domain model object whereby each Entity instance must have
 * a uniquely identifiable by a globally unique identifier as well as an
 * entity-unique version increment value which indicates the the current state
 * of the entity.
 *
 * @param <T> java class of the entity
 */
@Model(name="entity", domainName="acetate", domainVersion=1)
public interface EntityModel<T> extends ObjectModel<T> {

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
     * @return the model for the entity GUID
     */
    ValueModel<String> entityGuidModel();

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
     * @return model of the entity version component
     */
    ValueModel<Long> entityVersionModel();

}
