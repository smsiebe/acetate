package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.ComposableModelComponent;
import org.geoint.acetate.model.ContextualAddress;
import org.geoint.acetate.model.CompositeComponentModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public class ImmutableCompositeModel<T> extends ImmutableObjectModel<T>
        implements CompositeComponentModel<T> {

    private final ObjectModel<?> container;
    
    public ImmutableCompositeModel(
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<ComposableModelComponent> components,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        super(model, address, name, description, parentObjectNames, components,
                constraints, attributes, binaryCodec, charCodec);
    }
    
    @Override
    public ObjectModel<?> getContainer() {

    }

    @Override
    public ContextualAddress getAddress() {
        return (ContextualAddress) super.getAddress();
    }

    public static class ImmutableCompositeAddress
            extends ImmutableObjectAddress {

        private final ImmutableObjectAddress containerAddress;
        private final String localName;

        public ImmutableCompositeAddress(
                ImmutableObjectAddress containerAddress,
                String localName) {
            this.containerAddress = containerAddress;
            this.localName = localName;
        }

        @Override
        public String getDomainName() {
            return containerAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return containerAddress.getDomainVersion();
        }

        @Override
        public String asString() {
            return containerAddress.asString()
                    + ImmutableObjectAddress.COMPONENT_SEPARATOR
                    + localName;
        }
    }

}
