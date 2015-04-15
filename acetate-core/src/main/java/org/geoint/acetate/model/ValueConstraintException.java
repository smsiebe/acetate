package org.geoint.acetate.model;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when data did not meet constraint requirements of the data model.
 *
 * This exception <b>MUST NOT</b> contain any actual data so sensitive
 * information is not logged.
 */
public class ValueConstraintException extends AcetateException {

    private final DataModel model;
    private final Class<? extends ValueConstraint> failedConstraint;

    public ValueConstraintException(DataModel<?> model,
            Class<? extends ValueConstraint> failedConstraint,
            String constraintDetails) {
        super(constraintDetails);
        this.model = model;
        this.failedConstraint = failedConstraint;
    }

    public ValueConstraintException(DataModel<?> model,
            Class<? extends ValueConstraint> failedConstraint,
            String constraintDetails, Throwable cause) {
        super(constraintDetails, cause);
        this.model = model;
        this.failedConstraint = failedConstraint;
    }

    /**
     * Model of the field which failed constraint.
     *
     * @return field model
     */
    public DataModel getModel() {
        return model;
    }

    /**
     * The constraint type that was failed.
     *
     * @return constraint that tailed
     */
    public Class<? extends ValueConstraint> getFailedConstraint() {
        return failedConstraint;
    }

}
