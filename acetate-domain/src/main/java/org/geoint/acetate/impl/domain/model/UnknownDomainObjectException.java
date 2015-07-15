package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if an object of a domain model is referenced but is no known to the
 * domain model.
 */
public class UnknownDomainObjectException extends ModelException {

    private final ObjectId objectId;

    public UnknownDomainObjectException(ObjectId objectId, String message) {
        super(message);
        this.objectId = objectId;
    }

    public UnknownDomainObjectException(ObjectId objectId, String message, Throwable cause) {
        super(message, cause);
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

}
