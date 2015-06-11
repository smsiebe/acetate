package org.geoint.acetate.model;

/**
 * A Composite Object is a transient {@link ObjectModel domain model object}
 * which my be used in the composition of
 * {@link EntityObjectModel full-fledged domain model objects}.
 * <p>
 In comparison to an aggregate object, a composite object model does not have
 any identity itself, but rather encapsulates domain-significant state and
 operations. Composite objects, therefore, can be though of as not maintain
 their own state or executing their own behavior, but that on behalf of the
 entity object which contains it. This means that a Composite Object can
 neither be used as an Entity Object or referred to as an Aggregate Object
 within the model or applications using this model. The implications of this
 must be defined at the point of use of an ObjectModel.
 <p>
 * From a model perspective, a Composite Object essentially an extension of the
 * EntityObject (or AggregateObject) which contains it. Operations and
 * aggregates defined by the Composite object can be though of as "namespaced"
 * by the composite objects local name within the containing object, though any
 * use of these objects/operations is considered to be in context of the Entity
 * context it belongs. For example, a Composite Object defining an operation
 * which returns (fires) an {@link DomainEvent} is within the context of the
 * containing EntityObject, not that of the composite.
 * <p>
 * Typically, Composite Objects will only be used by Entity Objects within the
 * same domain model, though this is not enforced by default.
 *
 * @param <T> java data type of this domain object
 */
public interface CompositeModel<T> extends ObjectModel<T>,
        Composable<T> {

}
