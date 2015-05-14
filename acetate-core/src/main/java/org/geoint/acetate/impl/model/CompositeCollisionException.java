package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentPath;

/**
 * Thrown when there is a naming collision between two composites on a
 * component.
 */
public class CompositeCollisionException extends ComponentCollisionException {

    public CompositeCollisionException(ComponentPath contextPath) {
        super(contextPath);
    }

    public CompositeCollisionException(ComponentPath contextPath, String message) {
        super(contextPath, message);
    }

    public CompositeCollisionException(ComponentPath contextPath,
            String message, Throwable cause) {
        super(contextPath, message, cause);
    }

    public CompositeCollisionException(ComponentPath contextPath,
            Throwable cause) {
        super(contextPath, cause);
    }

}
