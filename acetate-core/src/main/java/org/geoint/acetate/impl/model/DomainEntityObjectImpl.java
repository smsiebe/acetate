package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.DomainAggregateObject;
import org.geoint.acetate.model.DomainCompositeObject;
import org.geoint.acetate.model.DomainEntityObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Entity Object domain object.
 *
 * @param <T>
 */
public class DomainEntityObjectImpl<T> extends DomainObjectImpl<T>
        implements DomainEntityObject<T> {

    private final DomainObject<String> entityGuidComponent;
    private final DomainObject<Long> entityVersionComponent;

    public DomainEntityObjectImpl(DomainModel model,
            ImmutableContextPath path,
            String name, String description, Collection<String> parentObjects,
            Collection<DomainOperation<?>> operations,
            Collection<DomainCompositeObject<?>> composites,
            Collection<DomainAggregateObject<?>> aggregates,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec) {
        super(model, path, name, description, parentObjects, operations,
                composites, aggregates, constraints, attributes, binaryCodec,
                charCodec);

        //cache the entity GUID and version components
        this.getComposites().stream()
                .filter((c) -> c.getAttributes().contains(AcetateDataAttribute.GUID))
                .findFirst();
    }

    @Override
    public DomainObject<String> getGuidComponentName() {
        return entityGuidComponent;
    }

    @Override
    public DomainObject<Long> getVersionComponentName() {
        return entityVersionComponent;
    }

}
