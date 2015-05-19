package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.constraint.Constrainable;

/**
 * The base model of an object used within a domain model, defining the common
 * characteristics of all concrete domain model object types.
 *
 * @see DomailEntityObject
 * @see DomainCompositeObject
 * @see DomainAggregateObject
 * @param <T> associated java data type
 */
public interface DomainObject<T> extends DomainComponent,
        Attributable, Constrainable {

    /**
     * Domain model unique name of domain object model.
     *
     * @return domain model unique component name
     */
    String getObjectName();

    /**
     * Returns all model operations, including all those defined natively on
     * this object model, inherited from any parent object model, as well as any
     * defined by any composite model.
     * <p>
     * If only native operations are desired (neither inherited or composite
     * operations), use the {@link #getNativeOperations() } method.
     * <p>
     * If only inherited operations are desired (neither native or composite
     * operations), iterate through the results of the {@link #inheritsFrom()}
     * method and call {@link #getNativeOperations() } on each result.
     * <p>
     * If only the composite object operations are desired, you may simply
     * iterate through the results of {@link #getComposites() } and call
     * {@link CompositeObjectModel#getNativeOperations()} on each result.
     *
     * @see DomainCompositeObject
     * @return component operations or empty collection if no behavior is found
     * on the component
     */
    Collection<DomainOperation<?>> getOperations();

    /**
     * Return only the model operations which are native to this model; not
     * including any
     *
     * @return
     */
    Collection<DomainOperation<?>> getNativeOperations();

    /**
     * Aggregate objects which are defined natively or by a
     * {@link DomainCompositeObject}.
     *
     * Aggregated objects are (other) domain model objects which can be
     * associated with this object through a defined relationship.
     *
     * @return child (composite) objects
     */
    Collection<DomainAggregateObject<?>> getAggregates();

    /**
     * Composites models from which this object model is comprised.
     *
     * A composite model is special in that the DomainObject essentially
     * considers its model components (operations/aggregates/components)
     * first-class components of its model, effectively combining a composite
     * model within the composite objects "namespace".
     *
     * Composite object models may "make up" this DomainObject, but does not
     * "relate" to another object model instance the way that an
     * AggregateObjectModel would. In other words, as DomainObject is "made up"
     * of composite model components which it "relates to" aggregate objects.
     *
     * @return
     */
    Collection<DomainCompositeObject<?>> getComposites();

    /**
     * Default character codec to use for this domain object.
     *
     * @return default character codec
     */
    CharacterCodec<T> getCharacterCodec();

    /**
     * Default binary codec to use for this domain object.
     *
     * @return default binary codec
     */
    BinaryCodec<T> getBinaryCodec();
}
