package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.model.address.ComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 * Business operation of the domain model.
 *
 * @param <R> operation return type
 */
public class ImmutableOperation<R> implements DomainOperation<R> {

    protected final DomainModel model;
    protected final ImmutableComponentAddress address;
    protected final String operationName;
    protected final Optional<String> description;
    protected final DomainEntityEvent<R, ?> returned;
    protected final Collection<DomainObject<?>> params;
    protected final Collection<? extends ComponentAttribute> attributes;

    ImmutableOperation(DomainModel model, 
            ImmutableComponentAddress path,
            String name,
            Optional<String> description,
            DomainEntityEvent<R, ?> returned,
            Collection<DomainObject<?>> params,
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
    public DomainObject<?> getDeclaringComponent() {

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
    public DomainEntityEvent<R, ?> getReturnModel() {
        return returned;
    }

    @Override
    public Collection<DomainObject<?>> getParameterModels() {
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
        final ImmutableOperation other = (ImmutableOperation) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }


}
