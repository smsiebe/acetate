package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.model.ThrowableModel;

/**
 *
 * @param <E>
 */
final class ImmutableThrowableModel implements ThrowableModel {

    private final String name;
    private final String domainName;
    private final MetaVersion domainVersion;

    public ImmutableThrowableModel(String domainName, MetaVersion domainVersion,
            String exceptionName) {
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.name = exceptionName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public MetaVersion getDomainVersion() {
        return domainVersion;
    }

}
