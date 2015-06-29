package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ModelException;

/**
 * Thrown if the inheritance characteristics of an object model is invalid.
 */
public class ModelInheritanceException extends ModelException {

    private final DomainId domainId;

    public ModelInheritanceException(DomainId domainId) {
        this.domainId = domainId;
    }

    public ModelInheritanceException(DomainId domainId, String message) {
        super(message);
        this.domainId = domainId;
    }

    public ModelInheritanceException(DomainId domainId, String message, Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
    }

    public ModelInheritanceException(DomainId domainId, Throwable cause) {
        super(cause);
        this.domainId = domainId;
    }

    public DomainId getDomainId() {
        return domainId;
    }

}
