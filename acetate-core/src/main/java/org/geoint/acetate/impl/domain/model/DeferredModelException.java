package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if there are problems instantiating a deferred object model.
 *
 */
public class DeferredModelException extends ModelException {

    private final ObjectId domainId;

    public DeferredModelException(ObjectId domainId) {
        this(domainId, defaultMessage(domainId));
    }

    public DeferredModelException(ObjectId domainId, String message) {
        super(message);
        this.domainId = domainId;
    }

    public DeferredModelException(ObjectId domainId, Throwable cause) {
        super(defaultMessage(domainId), cause);
        this.domainId = domainId;
    }

    public DeferredModelException(ObjectId domainId, String message,
            Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
    }

    public ObjectId getDomainId() {
        return domainId;
    }

    private static String defaultMessage(ObjectId domainId) {
        return "Unable to instantiate deferred "
                + "ObjectModel for type '"
                + domainId.asString() + "'";
    }
}
