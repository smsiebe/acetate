package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.Attributed;
import org.geoint.acetate.model.attribute.ModelAttribute;

/**
 *
 */
public final class ImmutableDomainModel implements DomainModel {

    private final InMemoryDomainModel model;

    private ImmutableDomainModel(String name,
            long version,
            Collection<ModelComponent> components)
            throws ModelException {
        this.model = new InMemoryDomainModel(name, version);
        for (ModelComponent c : components) {
            this.model.add(c);
        }
    }

    @Override
    public String getDomainId() {
        return model.getDomainId();
    }

    @Override
    public long getVersion() {
        return model.getVersion();
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public Collection<ModelComponent> findAll() {
        return model.findAll();
    }

    @Override
    public Optional<ModelComponent> find(ComponentAddress address) {
        return model.find(address);
    }

    @Override
    public Collection<Attributed> find(Class<? extends ModelAttribute> attributeType) {
        return model.find(attributeType);
    }

    @Override
    public Collection<ObjectModel<?>> findSpecialized(ComponentAddress parentAddress) {
        return model.findSpecialized(parentAddress);
    }

    @Override
    public String toString() {
        return model.toString();
    }

}
