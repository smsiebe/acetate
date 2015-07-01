package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if there was a parameter name collision.
 *
 */
public class DuplicateParametersException extends ModelException {

    private final ObjectId domainId;
    private final String parameterName;
    private final ObjectId existingParameterModel;
    private final ObjectId collisionParameterModel;

    public DuplicateParametersException(ObjectId domainId, String parameterName, ObjectId existingParameterModel, ObjectId collisionParameterModel) {
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(ObjectId domainId, String parameterName, ObjectId existingParameterModel, ObjectId collisionParameterModel, String message) {
        super(message);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(ObjectId domainId, String parameterName, ObjectId existingParameterModel, ObjectId collisionParameterModel, String message, Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(ObjectId domainId, String parameterName, ObjectId existingParameterModel, ObjectId collisionParameterModel, Throwable cause) {
        super(cause);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public ObjectId getDomainId() {
        return domainId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public ObjectId getExistingParameterModel() {
        return existingParameterModel;
    }

    public ObjectId getCollisionParameterModel() {
        return collisionParameterModel;
    }

}
