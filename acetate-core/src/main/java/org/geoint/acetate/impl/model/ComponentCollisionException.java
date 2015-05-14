package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentPath;
import org.geoint.acetate.model.ModelException;

/**
 * Thrown when a component could not be added to a domain because it conflicts
 * with an existing domain component.
 */
public class ComponentCollisionException extends ModelException {

    private final ComponentPath contextPath;

    public ComponentCollisionException(ComponentPath contextPath) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath));
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentPath contextPath, String message) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath) + message);
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentPath contextPath, String message,
            Throwable cause) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath) + message, cause);
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentPath contextPath, Throwable cause) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath), cause);
        this.contextPath = contextPath;
    }

    public ComponentPath getContextPath() {
        return contextPath;
    }

    private static String contextMessage(ComponentPath contextPath) {
        return "Duplicate model component '" + contextPath.asString() + "'. ";
    }
}
