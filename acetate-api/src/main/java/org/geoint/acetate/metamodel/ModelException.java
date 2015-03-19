package org.geoint.acetate.metamodel;

import java.util.Optional;
import org.geoint.acetate.AcetateException;

/**
 * Base class for exceptions that are thrown when there is a problem modeling
 * the object.
 */
public abstract class ModelException extends AcetateException {

    private final Optional<Class<?>> modelType;

    public ModelException(Class<?> modelType) {
        this.modelType = Optional.ofNullable(modelType);
    }

    public ModelException(Class<?> modelType, String message) {
        super(message);
        this.modelType = Optional.ofNullable(modelType);
    }

    public ModelException(Class<?> modelType, String message, Throwable cause) {
        super(message, cause);
        this.modelType = Optional.ofNullable(modelType);
    }

    public ModelException(Class<?> modelType, Throwable cause) {
        super(cause);
        this.modelType = Optional.ofNullable(modelType);
    }

    /**
     * Type of object that had modeling problems.
     *
     * @return class type of the model, if known
     */
    public Optional<Class<?>> getModelType() {
        return modelType;
    }

}
