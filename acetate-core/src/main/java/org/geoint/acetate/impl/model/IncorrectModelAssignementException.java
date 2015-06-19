package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ModelException;

/**
 * Thrown when a domain model component is improperly used with an unsupported
 * domain model.
 */
public class IncorrectModelAssignementException extends ModelException {

    private final String componentName;

    public IncorrectModelAssignementException(String domainModelName,
            long modelVersion, String componentName) {
        super(domainModelName, modelVersion);
        this.componentName = componentName;
    }

    public IncorrectModelAssignementException(String domainModelName,
            long modelVersion, String componentName, String message) {
        super(domainModelName, modelVersion, message);
        this.componentName = componentName;
    }

    public IncorrectModelAssignementException(String domainModelName,
            long modelVersion, String componentName, String message,
            Throwable cause) {
        super(domainModelName, modelVersion, message, cause);
        this.componentName = componentName;
    }

    public IncorrectModelAssignementException(String domainModelName,
            long modelVersion, String componentName, Throwable cause) {
        super(domainModelName, modelVersion, cause);
        this.componentName = componentName;
    }

    public String getComponentName() {
        return componentName;
    }

}
