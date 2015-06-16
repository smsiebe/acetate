package org.geoint.acetate.impl.model.entity;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableCompositeModel;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;
import org.geoint.acetate.impl.model.ImmutableObjectModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.entity.EntityModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.entity.EntityGuid;
import org.geoint.acetate.model.entity.EntityVersion;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Immutable Entity Object.
 *
 * @param <T> java object type this model defines
 */
public class ImmutableEntityModel<T> extends ImmutableObjectModel<T>
        implements EntityModel<T> {

    private final ObjectModel<String> entityGuidComponent;
    private final ObjectModel<Long> entityVersionComponent;

    public ImmutableEntityModel(DomainModel model,
            ImmutableComponentAddress path, String name, String description,
            Collection<String> parentObjectNames,
            Collection<ImmutableOperationModel<?>> operations,
            Collection<ImmutableCompositeModel<?>> composites,
            Collection<ImmutableAggregateModel<?>> aggregates,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        super(model, path, name, description, parentObjectNames, operations,
                composites, aggregates, constraints, attributes, binaryCodec,
                charCodec);

        //cache the entity GUID and version components
        ImmutableCompositeModel<String> guid = null;
        ImmutableCompositeModel<Long> version = null;
        for (ImmutableCompositeModel c : composites) {
            Collection<ComponentAttribute> cca = c.getAttributes();
            for (ComponentAttribute ca : cca) {
                if (ca.getClass().equals(EntityGuid.class)) {
                    guid = deconflict(guid, c);
                } else if (ca.getClass().equals(EntityVersion.class)) {

                }
            }
        }

    }

    /**
     * Attempts to deconflict a component collision based by inheritance, either
     * returning the component to use or throws an exception if the component
     * collision could not be deconflicted.
     *
     * @param <C>
     * @param existing
     * @param other
     * @return
     * @throws ComponentCollisionException
     */
    private <C extends ModelComponent> C deconflict(C c1, C c2)
            throws ComponentCollisionException {

    }

    @Override
    public Collection<ImmutableOperationModel<?>> getOperations() {
        return operations;
    }

    @Override
    public Collection<ImmutableAggregateModel<?>> getAggregates() {
        return aggregates;
    }

    @Override
    public ObjectModel<String> getGuidComponentName() {
        return entityGuidComponent;
    }

    @Override
    public ObjectModel<Long> getVersionComponentName() {
        return entityVersionComponent;
    }

}
