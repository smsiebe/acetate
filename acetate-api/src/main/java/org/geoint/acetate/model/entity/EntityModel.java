package org.geoint.acetate.model.entity;

import java.util.Collection;
import org.geoint.acetate.model.ObjectModel;

/**
 * An Entity Object instance is a object with the domain model which is
 * identifiable by a globally unique identifier and who's specific state is
 * identifiable by an entity-unique version increment.
 *
 * Any Entity Object within a domain model may be addressed directly by its
 * unique identity (and version), from which its state and operations (defined
 * by composite and aggregate objects) may be accessed.
 *
 * @param <T> java class of the entity
 */
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
     * @return the aggregate/composite domain object which is the globally
     * unique identifier of the entity
     */
    ObjectModel<String> getGuidComponent();

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
    ObjectModel<Long> getVersionComponent();

    /**
     * Aggregate objects which are defined natively, inheritance, or by a
     * {@link DomainCompositeObject}.
     *
     * Aggregated objects are (other) Entity Objects which can be associated
     * with this object through a model-defined relationship.
     * <p>
     * To receive a only aggregates that were natively declared, inherited, or
     * were brought in from a component relationship, you can check the data
     * component attributes of each aggregate returned from this method for
     * either the {@link Inherited} or {@link Composited} attribute (the absence
     * of either of these indicates that it was a native aggregate).
     *
     * @return all aggregate objects
     */
    Collection<? extends AggregateModel<?>> getAggregates();

    /**
     * Returns all Entity operations, including all those defined natively on
     * this object model, inherited from any parent object model, as well as any
     * defined by any composite model.
     *
     * To receive a only operations that were natively declared, inherited, or
     * were brought in from a component relationship, you can check the data
     * component attributes of each operation returned from this method for
     * either the {@link Inherited} or {@link Composited} attribute (the absence
     * of either of these indicates that it was a native operation).
     *
     * @see DomainCompositeObject
     * @return component operations or empty collection if no behavior is found
     * on the component
     */
    Collection<? extends OperationModel<?>> getOperations();
}
