package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ModelException;

/**
 * Thrown if there are problems instantiating a deferred object model.
 *
 */
public class DeferredModelException extends ModelException {

    private final DomainId domainId;

    public DeferredModelException(DomainId domainId) {
        this(domainId, defaultMessage(domainId));
    }

    public DeferredModelException(DomainId domainId, String message) {
        super(message);
        this.domainId = domainId;
    }

    public DeferredModelException(DomainId domainId, Throwable cause) {
        super(defaultMessage(domainId), cause);
        this.domainId = domainId;
    }

    public DeferredModelException(DomainId domainId, String message,
            Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
    }

    public DomainId getDomainId() {
        return domainId;
    }

    private static String defaultMessage(DomainId domainId) {
        return "Unable to instantiate deferred "
                + "ObjectModel for type '"
                + domainId.asString() + "'";
    }
}
