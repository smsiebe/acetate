package org.geoint.acetate.impl.domain.model;

import java.util.Objects;
import org.geoint.acetate.domain.model.ExceptionModel;
import org.geoint.acetate.domain.model.MetaVersion;

/**
 *
 */
final class ImmutableThrowableModel implements ExceptionModel {

    private final ObjectId objectId;

    public ImmutableThrowableModel(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ImmutableThrowableModel(String domainName, MetaVersion domainVersion,
            String exceptionName) {
        this(ObjectId.getInstance(domainName, domainVersion, exceptionName));
    }

    @Override
    public String getName() {
        return objectId.getObjectName();
    }

    @Override
    public String getDomainName() {
        return objectId.getDomainName();
    }

    @Override
    public MetaVersion getDomainVersion() {
        return objectId.getDomainVersion();
    }

    @Override
    public String toString() {
        return objectId.asString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.objectId);
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
        final ImmutableThrowableModel other = (ImmutableThrowableModel) obj;
        if (!Objects.equals(this.objectId, other.objectId)) {
            return false;
        }
        return true;
    }

}
