package org.geoint.acetate.model;

import java.lang.annotation.Inherited;
import java.util.Collection;
import java.util.Set;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.attribute.Composited;
import org.geoint.acetate.model.constraint.Constrained;

/**
 * The base model of an object used within a domain model, defining the common
 * characteristics of all concrete domain model object types.
 *
 * @see DomailEntityObject
 * @see DomainCompositeObject
 * @see AggregateModel
 * @param <T> associated java data type
 */
public interface ObjectModel<T> extends ModelComponent<T>,
        Constrained, Attributable {

    /**
     * Domain model unique display name of domain object.
     *
     * @return domain model unique component name
     */
    String getObjectName();

    /**
     * Domain object model(s) this object inherits from, potentially inheriting
     * components.
     *
     * @return domain component from which this component is inherited
     */
    Set<ObjectModel<? super T>> getParents();

    /**
     * Returns all object operations, including all those defined natively on
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
     * Composites objects from which this object model is comprised.
     *
     * A composite model is special in that the ObjectModel essentially
 considers its model components (operations/aggregates/components)
 first-class components of its model, effectively combining a composite
 model within the composite objects "namespace".
 <p>
 Composite object models may "make up" this ObjectModel, but does not
 "relate" to another object model instance the way that an
 AggregateObjectModel would. In other words, as ObjectModel is "made up"
 of composite model components which it "relates to" aggregate objects.
 <p>
     * To receive a only composites that were natively declared, inherited, or
     * were brought in from a component relationship, you can check the data
     * component attributes of each composite returned from this method for
     * either the {@link Inherited} or {@link Composited} attribute (the absence
     * of either of these indicates that it was a native composite).
     *
     * @return collection of composite objects defined natively, through
     * composites, or inheritance
     */
    Collection<? extends Composable<?>> getComposites();

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
