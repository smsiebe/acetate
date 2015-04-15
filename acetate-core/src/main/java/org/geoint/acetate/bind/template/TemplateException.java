package org.geoint.acetate.bind.template;

/**
 * Thrown when there is a problem with a {@link DataTemplate}.
 */
public class TemplateException extends Exception {

    private final Class<?> modelType;

    public TemplateException(Class<?> modelType) {
        this.modelType = modelType;
    }

    public TemplateException(Class<?> modelType, String message) {
        super(message);
        this.modelType = modelType;
    }

    public TemplateException(Class<?> modelType, String message,
            Throwable cause) {
        super(message, cause);
        this.modelType = modelType;
    }

    public TemplateException(Class<?> modelType, Throwable cause) {
        super(cause);
        this.modelType = modelType;
    }

    public Class<?> getModelType() {
        return modelType;
    }

}
