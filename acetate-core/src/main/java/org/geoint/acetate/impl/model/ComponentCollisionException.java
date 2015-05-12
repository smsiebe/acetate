package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.ModelException;

/**
 * Thrown when a component could not be added to a domain because it conflicts
 * with an existing domain component.
 */
public class ComponentCollisionException extends ModelException {

    private final ComponentModel<?> existingComponent;
    private final ComponentModel<?> conflictingComponent;

    public ComponentCollisionException(String domainModelName,
            long modelVersion, ComponentModel<?> existingComponent,
            ComponentModel<?> conflictingComponent) {
        super(domainModelName, modelVersion);
        this.existingComponent = existingComponent;
        this.conflictingComponent = conflictingComponent;
    }

    public ComponentCollisionException(String domainModelName, long modelVersion,
            ComponentModel<?> existingComponent,
            ComponentModel<?> conflictingComponent,
            String message) {
        super(domainModelName, modelVersion, message);
        this.existingComponent = existingComponent;
        this.conflictingComponent = conflictingComponent;
    }

    public ComponentCollisionException(String domainModelName, long modelVersion,
            ComponentModel<?> existingComponent,
            ComponentModel<?> conflictingComponent,
            String message, Throwable cause) {
        super(domainModelName, modelVersion, message, cause);
        this.existingComponent = existingComponent;
        this.conflictingComponent = conflictingComponent;
    }

    public ComponentCollisionException(String domainModelName, long modelVersion,
            ComponentModel<?> existingComponent,
            ComponentModel<?> conflictingComponent, Throwable cause) {
        super(domainModelName, modelVersion, cause);
        this.existingComponent = existingComponent;
        this.conflictingComponent = conflictingComponent;
    }

    public ComponentModel<?> getExistingComponent() {
        return existingComponent;
    }

    public ComponentModel<?> getConflictingComponent() {
        return conflictingComponent;
    }

}
