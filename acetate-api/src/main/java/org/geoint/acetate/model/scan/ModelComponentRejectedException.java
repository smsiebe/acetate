package org.geoint.acetate.model.scan;

import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 * Thrown by a {@link ModelComponentListener} to reject a domain model
 * component.
 */
public class ModelComponentRejectedException extends ModelException {

    private final ModelComponent component;

    public ModelComponentRejectedException(ModelComponent component,
            String domainModelName, long modelVersion) {
        super(domainModelName, modelVersion);
        this.component = component;
    }

    public ModelComponentRejectedException(ModelComponent component,
            String domainModelName, long modelVersion, String message) {
        super(domainModelName, modelVersion, message);
        this.component = component;
    }

    public ModelComponentRejectedException(ModelComponent component,
            String domainModelName, long modelVersion, String message, Throwable cause) {
        super(domainModelName, modelVersion, message, cause);
        this.component = component;
    }

    public ModelComponentRejectedException(ModelComponent component,
            String domainModelName, long modelVersion, Throwable cause) {
        super(domainModelName, modelVersion, cause);
        this.component = component;
    }

    public ModelComponent getComponent() {
        return component;
    }

}
