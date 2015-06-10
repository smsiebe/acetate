package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.CompositeComponent;
import org.geoint.acetate.model.DomainAggregateObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableAggregate<T> extends ImmutableDomainObject<T>
        implements DomainAggregateObject<T> {

    public ImmutableAggregate(DomainModel model,
            ImmutableComponentAddress address,
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<CompositeComponent> components,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        super(model, address, name, description, parentObjectNames,
                components, constraints, attributes, binaryCodec, charCodec);
    }


    @Override
    public boolean isCollection() {

    }

    @Override
    public DomainObject<String> getGuidComponent() {

    }

    @Override
    public DomainObject<Long> getVersionComponent() {

    }

    @Override
    public String getLocalName() {

    }

}
