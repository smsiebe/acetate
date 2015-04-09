package org.geoint.acetate.model;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when data did not meet constraint requirements of the data model.
 *
 * This exception <b>MUST NOT</b> contain any actual data so sensitive
 * information is not logged.
 */
public class DataConstraintException extends AcetateException {

    private final FieldModel fieldModel;
    private final Class<? extends DataConstraint> failedConstraint;

    public DataConstraintException(FieldModel<?> field,
            Class<? extends DataType> dataType,
            Class<? extends DataConstraint> failedConstraint,
            String constraintDetails) {
        super(constraintDetails);
        this.fieldModel = field;
        this.failedConstraint = failedConstraint;
    }

    public DataConstraintException(FieldModel<?> field,
            Class<? extends DataType> dataType,
            Class<? extends DataConstraint> failedConstraint,
            String constraintDetails, Throwable cause) {
        super(constraintDetails, cause);
        this.fieldModel = field;
        this.failedConstraint = failedConstraint;
    }

    /**
     * Model of the field which failed constraint.
     *
     * @return field model
     */
    public FieldModel getModel() {
        return fieldModel;
    }

    /**
     * The constraint type that was failed.
     *
     * @return constraint that tailed
     */
    public Class<? extends DataConstraint> getFailedConstraint() {
        return failedConstraint;
    }

}
