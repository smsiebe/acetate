package org.geoint.acetate.metamodel;

import org.geoint.acetate.AcetateException;

/**
 * Base class for exceptions that are thrown when there is a problem modeling
 * the object.
 */
public abstract class ModelException extends AcetateException {

    private final Class<?> modelType;

    public ModelException(Class<?> modelType) {
        this.modelType = modelType;
    }

    public ModelException(Class<?> modelType, String message) {
        super(message);
        this.modelType = modelType;
    }

    public ModelException(Class<?> modelType, String message, Throwable cause) {
        super(message, cause);
        this.modelType = modelType;
    }

    public ModelException(Class<?> modelType, Throwable cause) {
        super(cause);
        this.modelType = modelType;
    }

    /**
     * Type of object that had modeling problems.
     *
     * @return
     */
    public Class<?> getModelType() {
        return modelType;
    }

}
