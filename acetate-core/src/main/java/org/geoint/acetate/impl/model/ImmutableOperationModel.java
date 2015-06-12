package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableCompositeAddress;
import org.geoint.acetate.impl.model.ImmutableObjectModel.ImmutableObjectAddress;
import org.geoint.acetate.model.ModelAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.OperationModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.EventModel;

/**
 * Business operation of the domain model.
 *
 * @param <R> operation return event type
 */
public class ImmutableOperationModel<R> implements OperationModel<R> {

    protected final DomainModel model;
    protected final ImmutableObjectAddress address;
    protected final String operationName;
    protected final Optional<String> description;
    protected final EventModel<R, ?> returned;
    protected final Collection<ObjectModel<?>> params;
    protected final Collection<? extends ComponentAttribute> attributes;

    ImmutableOperationModel(DomainModel model, 
            ImmutableObjectAddress path,
            String name,
            Optional<String> description,
            EventModel<R, ?> returned,
            Collection<ObjectModel<?>> params,
            Collection<? extends ComponentAttribute> attributes) {
        this.model = model;
        this.address = path;
        this.operationName = name;
        this.description = description;
        this.returned = returned;
        this.params = params;
        this.attributes = attributes;
    }

    @Override
    public DomainModel getDomainModel() {
        return model;
    }

    @Override
    public ObjectModel<?> getDeclaringComponent() {

    }

    @Override
    public ModelAddress getAddress() {
        return address;
    }

    @Override
    public String getLocalName() {
        return operationName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return (Collection<ComponentAttribute>) attributes;
    }

    @Override
    public EventModel<R, ?> getReturnModel() {
        return returned;
    }

    @Override
    public Collection<ObjectModel<?>> getParameterModels() {
        return params;
    }

    @Override
    public String toString() {
        return address.asString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableOperationModel other = (ImmutableOperationModel) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }


    public static class ImmutableOperationAddress
            extends ImmutableCompositeAddress {

        public ImmutableOperationAddress(
                ImmutableObjectAddress containerAddress,
                String operationName) {
            super(containerAddress, operationName);
        }

        public ImmutableCompositeAddress parameter(String paramName) {
            return new ImmutableOperationParameterAddress(this, paramName);
        }

        public ImmutableOperationAddress returns() {
            return new 
        }

    }
}
