package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if the inheritance characteristics of an object model is invalid.
 */
public class ModelInheritanceException extends ModelException {

    private final ObjectId domainId;

    public ModelInheritanceException(ObjectId domainId) {
        this.domainId = domainId;
    }

    public ModelInheritanceException(ObjectId domainId, String message) {
        super(message);
        this.domainId = domainId;
    }

    public ModelInheritanceException(ObjectId domainId, String message, Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
    }

    public ModelInheritanceException(ObjectId domainId, Throwable cause) {
        super(cause);
        this.domainId = domainId;
    }

    public ObjectId getDomainId() {
        return domainId;
    }

}
