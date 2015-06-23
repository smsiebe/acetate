package org.geoint.acetate.model;

import org.geoint.acetate.meta.model.ModelException;

/**
 * Thrown if the domain model identifier was invalid, was not formatted
 * properly, or could not be determined.
 */
public class InvalidDomainIdentifierException extends ModelException {

    private final String domainId;

    public InvalidDomainIdentifierException(String domainId) {
        super(null, 0);
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, String message) {
        super(null, 0, message);
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, String message,
            Throwable cause) {
        super(null, 0, message, cause);
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, Throwable cause) {
        super(null, 0, cause);
        this.domainId = domainId;
    }

    public String getDomainId() {
        return domainId;
    }

}
