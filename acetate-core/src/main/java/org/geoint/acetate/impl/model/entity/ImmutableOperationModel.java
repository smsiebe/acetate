package org.geoint.acetate.impl.model.entity;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.geoint.acetate.impl.model.ImmutableCompositeModel.ImmutableCompositeAddress;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableCompositeAddress;
import org.geoint.acetate.impl.model.ImmutableObjectModel.ImmutableObjectAddress;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.entity.OperationModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.entity.EventModel;

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
    public ObjectModel<?> getContainer() {

    }

    @Override
    public ComponentAddress getAddress() {
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
            extends ImmutableObjectAddress {

        private final ImmutableObjectAddress containerAddress;
        private final String operationName;

        private static final char OPERATION_SEPARATOR = '#';

        public ImmutableOperationAddress(
                ImmutableObjectAddress containerAddress,
                String operationName) {
            this.containerAddress = containerAddress;
            this.operationName = operationName;
        }

        public String getOperationName() {
            return operationName;
        }

        @Override
        public String getDomainName() {
            return containerAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return containerAddress.getDomainVersion();
        }

        public ImmutableObjectAddress parameter(String paramName) {
            return new ImmutableOperationParameterAddress(this, paramName);
        }

        public ImmutableObjectAddress returns() {
            return new ImmutableOperationReturnAddress(this);
        }

        @Override
        public String asString() {
            return containerAddress.asString()
                    + OPERATION_SEPARATOR
                    + operationName;
        }

    }

    public static class ImmutableOperationParameterAddress
            extends ImmutableObjectAddress {

        private final ImmutableOperationAddress operationAddress;
        private final String paramName;

        private static final char PARAMETER_SEPARATOR = '?';

        public ImmutableOperationParameterAddress(
                ImmutableOperationAddress operationAddress,
                String paramName) {
            this.operationAddress = operationAddress;
            this.paramName = paramName;
        }

        public String getParameterName() {
            return paramName;
        }

        @Override
        public String getDomainName() {
            return operationAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return operationAddress.getDomainVersion();
        }

        @Override
        public String asString() {
            return operationAddress.asString()
                    + PARAMETER_SEPARATOR
                    + paramName;
        }

    }

    public static class ImmutableOperationReturnAddress
            extends ImmutableObjectAddress {

        private final ImmutableOperationAddress operationAddress;

        private static final char RETURN_SEPARATOR = '>';

        public ImmutableOperationReturnAddress(
                ImmutableOperationAddress operationAddress) {
            this.operationAddress = operationAddress;
        }

        @Override
        public String getDomainName() {
            return operationAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return operationAddress.getDomainVersion();
        }

        @Override
        public String asString() {
            return operationAddress.asString()
                    + RETURN_SEPARATOR;
        }

    }
}
