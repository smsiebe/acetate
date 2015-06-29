package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ModelException;

/**
 * Thrown if there was a parameter name collision.
 *
 */
public class DuplicateParametersException extends ModelException {

    private final DomainId domainId;
    private final String parameterName;
    private final DomainId existingParameterModel;
    private final DomainId collisionParameterModel;

    public DuplicateParametersException(DomainId domainId, String parameterName, DomainId existingParameterModel, DomainId collisionParameterModel) {
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(DomainId domainId, String parameterName, DomainId existingParameterModel, DomainId collisionParameterModel, String message) {
        super(message);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(DomainId domainId, String parameterName, DomainId existingParameterModel, DomainId collisionParameterModel, String message, Throwable cause) {
        super(message, cause);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DuplicateParametersException(DomainId domainId, String parameterName, DomainId existingParameterModel, DomainId collisionParameterModel, Throwable cause) {
        super(cause);
        this.domainId = domainId;
        this.parameterName = parameterName;
        this.existingParameterModel = existingParameterModel;
        this.collisionParameterModel = collisionParameterModel;
    }

    public DomainId getDomainId() {
        return domainId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public DomainId getExistingParameterModel() {
        return existingParameterModel;
    }

    public DomainId getCollisionParameterModel() {
        return collisionParameterModel;
    }

}
