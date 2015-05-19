package org.geoint.acetate.model;

import java.util.Collection;

/**
 * An Aggregate Object is one that defines a relationship between two Entity
 * Objects.
 *
 * The aggregate relationship "type" is defined by the domain model itself, the
 * details of which is accessible from this model definition.
 * <p>
 * Often when considering object models/graphs, "directionality" of a
 * relationship (unidirectional, bidirectional, etc) is a consideration. This
 * directionality, from a model/systems perspective, allows tools to consider
 * this in its management of the model or the data. Instead of a strict or
 * statically defined directionality relationship in the domain model itself,
 * applications are expected to make use of DomainEvents to register/receive
 * updates about DomainEntityObject instances of interest, keeping the domain
 * model decoupled from application-specific implementation details.
 *
 * @param <T> type of the aggregated object
 */
public interface DomainAggregateObject<T> extends DomainEntityObject<T> {

    /**
     * The domain component which defines the possible relationship types
     * supported by the domain model.
     *
     * @return a domain component which defines the relationships as a optional
     * list of strings
     */
    DomainComponent<Collection<String>> possibleRelationshipTypes();

    /**
     * The domain component which defines the relationship state of a the
     * relationship between two entity objects.
     *
     * The domain component <b>must</b> return a relationship which is listed by
     * the component declaring the possible relationship types.
     *
     * @see #possibleRelationshipTypes()
     * @return the domain component which defines the current relationship state
     * between the two entity objects
     */
    DomainComponent<String> relationshipObject();

}
