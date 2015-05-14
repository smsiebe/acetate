package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.registry.ComponentRegistry;

/**
 *
 */
public final class ImmutableDomainModel implements DomainModel {

    private final String domainId;
    private final String name;
    private final long version;
    private final String displayName;
    private final Optional<String> description;
    private final ComponentRegistry registry;

    public ImmutableDomainModel(ComponentRegistry registry, String domainId,
            String name, long version) {
        this(registry, domainId, name, version, null, null);
    }

    public ImmutableDomainModel(ComponentRegistry registry, String domainId,
            String name, long version, String displayName) {
        this(registry, domainId, name, version, displayName, null);
    }

    public ImmutableDomainModel(ComponentRegistry registry, String domainId,
            String name, long version, String displayName, String description) {
        this.domainId = domainId;
        this.name = name;
        this.version = version;
        this.displayName = displayName;
        this.description = Optional.ofNullable(description);
        this.registry = registry;
    }

    @Override
    public String getDomainId() {
        return domainId;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return (displayName != null) ? displayName : toString();
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ObjectModel<?>> getComponents() {
        return registry.findAll();
    }

    @Override
    public String toString() {
        return DomainUtil.uniqueDomainId(name, version);
    }

}
