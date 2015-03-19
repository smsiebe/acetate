package org.geoint.acetate.metamodel;

/**
 * Thrown when a component (aggregate or field) could not be added to a model
 * because there was a name collision.
 */
public class ModelComponentCollisionException extends ModelException {

    private final String componentName;

    public ModelComponentCollisionException(Class<?> modelType,
            String componentName) {
        super(modelType);
        this.componentName = componentName;
    }

    public ModelComponentCollisionException(Class<?> modelType,
            String componentName,
            String message) {
        super(modelType, message);
        this.componentName = componentName;
    }

    public ModelComponentCollisionException(Class<?> modelType,
            String componentName, String message, Throwable cause) {
        super(modelType, message, cause);
        this.componentName = componentName;
    }

    /**
     * Name of the component that could not be added.
     *
     * @return name of the component
     */
    public String getComponentName() {
        return componentName;
    }

}
