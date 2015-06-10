package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableComponentAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainEntityObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.attribute.EntityGuid;
import org.geoint.acetate.model.attribute.EntityVersion;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Immutable Entity Object.
 *
 * @param <T> java object type this model defines
 */
public class ImmutableEntity<T> extends ImmutableDomainObject<T>
        implements DomainEntityObject<T> {

    private final DomainObject<String> entityGuidComponent;
    private final DomainObject<Long> entityVersionComponent;

    public ImmutableEntity(DomainModel model,
            ImmutableComponentAddress path, String name, String description,
            Collection<String> parentObjectNames,
            Collection<ImmutableOperation<?>> operations,
            Collection<ImmutableCompositeObject<?>> composites,
            Collection<ImmutableAggregate<?>> aggregates,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        super(model, path, name, description, parentObjectNames, operations,
                composites, aggregates, constraints, attributes, binaryCodec,
                charCodec);

        //cache the entity GUID and version components
        ImmutableCompositeObject<String> guid = null;
        ImmutableCompositeObject<Long> version = null;
        for (ImmutableCompositeObject c : composites) {
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
    private <C extends DomainComponent> C deconflict(C c1, C c2)
            throws ComponentCollisionException {
        
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
