package org.geoint.acetate.model;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when bound data does not meet model constraint conditions.
 *
 */
public class ModelConstraintException extends AcetateException {

    private final ComponentModel model;
    private final Class<? extends ComponentConstraint> failedConstraint;

    public ModelConstraintException(ComponentModel model,
            Class<? extends ComponentConstraint> failedConstraint,
            String message) {
        super(message);
        this.model = model;
        this.failedConstraint = failedConstraint;
    }

    public ModelConstraintException(ComponentModel model,
            Class<? extends ComponentConstraint> failedConstraint,
            String message, Throwable cause) {
        super(message, cause);
        this.model = model;
        this.failedConstraint = failedConstraint;
    }

    /**
     * Component model that failed the constraint.
     *
     * @return component which failed the constraint
     */
    public ComponentModel getModel() {
        return model;
    }

    /**
     * Model constraint class which was not passed.
     *
     * @return
     */
    public Class<? extends ComponentConstraint> getFailedConstraint() {
        return failedConstraint;
    }

}
