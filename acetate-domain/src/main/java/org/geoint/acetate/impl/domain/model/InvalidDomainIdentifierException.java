package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.domain.model.ModelException;


/**
 * Thrown if the domain model identifier was invalid, was not formatted
 * properly, or could not be determined.
 */
public class InvalidDomainIdentifierException extends ModelException {

    private final String domainId;

    public InvalidDomainIdentifierException(String domainId) {
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, String message) {
        super(message);
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, String message,
            Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
    }

    public InvalidDomainIdentifierException(String domainId, Throwable cause) {
        super(cause);
        this.domainId = domainId;
    }

    public String getDomainId() {
        return domainId;
    }

}
