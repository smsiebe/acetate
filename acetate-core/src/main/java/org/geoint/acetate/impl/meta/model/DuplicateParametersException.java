package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ModelException;

/**
 * Thrown if there was a parameter name collision.
 *
 */
public class DuplicateParametersException extends ModelException {

    private final Class<?> type;
    private final String parameterName;
    private final Class<?> existingParameterType;
    private final Class<?> collisionParameterType;

    public DuplicateParametersException(Class<?> type, String parameterName, Class<?> existingParameterType, Class<?> collisionParameterType) {
        this.type = type;
        this.parameterName = parameterName;
        this.existingParameterType = existingParameterType;
        this.collisionParameterType = collisionParameterType;
    }

    public DuplicateParametersException(Class<?> type, String parameterName, Class<?> existingParameterType, Class<?> collisionParameterType, String message) {
        super(message);
        this.type = type;
        this.parameterName = parameterName;
        this.existingParameterType = existingParameterType;
        this.collisionParameterType = collisionParameterType;
    }

    public DuplicateParametersException(Class<?> type, String parameterName, Class<?> existingParameterType, Class<?> collisionParameterType, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
        this.parameterName = parameterName;
        this.existingParameterType = existingParameterType;
        this.collisionParameterType = collisionParameterType;
    }

    public DuplicateParametersException(Class<?> type, String parameterName, Class<?> existingParameterType, Class<?> collisionParameterType, Throwable cause) {
        super(cause);
        this.type = type;
        this.parameterName = parameterName;
        this.existingParameterType = existingParameterType;
        this.collisionParameterType = collisionParameterType;
    }

    public Class<?> getType() {
        return type;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Class<?> getExistingParameterType() {
        return existingParameterType;
    }

    public Class<?> getCollisionParameterType() {
        return collisionParameterType;
    }

}
