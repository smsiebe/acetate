package org.geoint.acetate.model.builder;

import org.geoint.acetate.model.ComponentContextPath;
import org.geoint.acetate.model.ModelException;

/**
 * Thrown when a component could not be added to a domain because it conflicts
 * with an existing domain component.
 */
public class ComponentCollisionException extends ModelException {

    private final ComponentContextPath contextPath;

    public ComponentCollisionException(ComponentContextPath contextPath) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath));
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentContextPath contextPath, String message) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath) + message);
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentContextPath contextPath, String message,
            Throwable cause) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath) + message, cause);
        this.contextPath = contextPath;
    }

    public ComponentCollisionException(ComponentContextPath contextPath, Throwable cause) {
        super(contextPath.getDomainName(), contextPath.getDomainVersion(),
                contextMessage(contextPath), cause);
        this.contextPath = contextPath;
    }

    public ComponentContextPath getContextPath() {
        return contextPath;
    }

    private static String contextMessage(ComponentContextPath contextPath) {
        return "Duplicate model component '" + contextPath.asString() + "'. ";
    }
}
