package org.geoint.acetate.impl.model;

import java.util.Optional;
import org.geoint.acetate.data.transform.CodecRegistry;
import org.geoint.acetate.data.transform.ConverterRegistry;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectRegistry;

/**
 * The default domain model has fixed domain model (components of the core
 * domain model cannot change after construction) but its does support the
 * registration of contextual model variants (ie views).
 */
public final class DomainModelImpl implements DomainModel {

    private final String domainId;
    private final String name;
    private final long version;
    private final String displayName;
    private final Optional<String> description;
    private final ObjectRegistry modelRegistry;

    public DomainModelImpl(ObjectRegistry registry, String domainId,
            String name, long version) {
        this(registry, domainId, name, version, null, null);
    }

    public DomainModelImpl(ObjectRegistry registry, String domainId,
            String name, long version, String displayName) {
        this(registry, domainId, name, version, displayName, null);
    }

    public DomainModelImpl(ObjectRegistry modelRegistry,
            String domainId,
            String name, long version, String displayName, String description) {
        this.modelRegistry = modelRegistry;

        this.domainId = domainId;
        this.name = name;
        this.version = version;
        this.displayName = displayName;
        this.description = Optional.ofNullable(description);
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
    public ObjectRegistry getComponents() {
        return modelRegistry;
    }

    @Override
    public String toString() {
        return DomainUtil.uniqueDomainId(name, version);
    }

}
