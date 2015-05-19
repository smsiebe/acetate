package org.geoint.acetate.model.builder;

import org.geoint.acetate.model.ModelContextPath;

/**
 * Thrown when there is a naming collision between two composites on a
 * component.
 */
public class CompositeCollisionException extends ComponentCollisionException {

    public CompositeCollisionException(ModelContextPath contextPath) {
        super(contextPath);
    }

    public CompositeCollisionException(ModelContextPath contextPath, String message) {
        super(contextPath, message);
    }

    public CompositeCollisionException(ModelContextPath contextPath,
            String message, Throwable cause) {
        super(contextPath, message, cause);
    }

    public CompositeCollisionException(ModelContextPath contextPath,
            Throwable cause) {
        super(contextPath, cause);
    }

}
