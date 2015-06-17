package org.geoint.acetate.impl.model.entity;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableObjectModel;
import org.geoint.acetate.model.ContextualModelComponent;
import org.geoint.acetate.model.entity.AggregateModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableAggregateModel<T> extends ImmutableObjectModel<T>
        implements AggregateModel<T> {

    public ImmutableAggregateModel(DomainModel model,
            ImmutableObjectAddress address,
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<ContextualModelComponent> components,
            Collection<DataConstraint> constraints,
            Collection<ModelAttribute> attributes,
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
    public ObjectModel<String> getGuidComponent() {

    }

    @Override
    public ObjectModel<Long> getVersionComponent() {

    }

    @Override
    public String getLocalName() {

    }

}
