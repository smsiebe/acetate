package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if there was a parameter name collision.
 *
 */
public class DuplicateParametersException extends ModelException {

    private final ObjectId objectId;
    private final String operationName;
    private final String parameterName;
    private final ObjectId existingParameterModel;
    private final ObjectId collisionParameterModel;

    public DuplicateParametersException(ObjectId objectId, String operationName,
            String parameterName, ObjectId existingParameterModel,
            ObjectId collisionParameterModel) {
        this(objectId,
                operationName,
                parameterName,
                existingParameterModel,
                collisionParameterModel,
                defaultMessage(objectId,
                        operationName,
                        parameterName,
                        existingParameterModel,
                        collisionParameterModel
                )
        );
    }

    public DuplicateParametersException(ObjectId objectId, String operationName, String parameterName,
            ObjectId existingParameterModel, ObjectId collisionParameterModel,
            String message) {
        super(message);
        this.objectId = objectId;
        this.operationName = operationName;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(ObjectId objectId, String operationName, String parameterName,
            ObjectId existingParameterModel, ObjectId collisionParameterModel,
            String message, Throwable cause) {
        super(message, cause);
        this.objectId = objectId;
        this.operationName = operationName;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(ObjectId objectId, String operationName, String parameterName,
            ObjectId existingParameterModel, ObjectId collisionParameterModel,
            Throwable cause) {
        super(cause);
        this.objectId = objectId;
        this.operationName = operationName;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public ObjectId getDomainId() {
        return objectId;
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

    private static String defaultMessage(ObjectId objectId, String operationName,
            String parameterName, ObjectId existingParameterModel,
            ObjectId collisionParameterModel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Duplicate operation parameter '")
                .append(parameterName)
                .append("' on '")
                .append(objectId.asString())
                .append("#")
                .append(operationName)
                .append("'.  Existing parameter model '")
                .append(existingParameterModel.asString())
                .append("' does not match duplicate parameter model '")
                .append(collisionParameterModel.asString())
                .append("'");
        return sb.toString();
    }
}
