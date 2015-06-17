package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.ContextualModelComponent;
import org.geoint.acetate.model.ContextualAddress;
import org.geoint.acetate.model.ContextualComponentModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 * Model of a component contextually contained by (composes) another component.
 *
 * @param <T> java type representation of the component
 */
public class ImmutableCompositeObjectModel<T> extends ImmutableObjectModel<T>
        implements ContextualModelComponent<ObjectModel<T>> {

    private final ObjectModel<?> container;
    private final ObjectModel<T> baseModel;

    public ImmutableCompositeObjectModel(
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<ContextualModelComponent> components,
            Collection<DataConstraint> constraints,
            Collection<ModelAttribute> attributes,
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

    @Override
    public Collection<? extends ContextualComponentModel> getComposites() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
