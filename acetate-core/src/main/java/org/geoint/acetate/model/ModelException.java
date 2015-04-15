package org.geoint.acetate.model;

/**
 * Thrown when there are problems modeling a data model.
 */
public class ModelException extends Exception {

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

    public Class<?> getModelType() {
        return modelType;
    }

}
