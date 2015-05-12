package org.geoint.acetate.model;

import gov.ic.geoint.acetate.AcetateException;

/**
 *
 */
public class ModelException extends AcetateException {

    private final String domainModelName;
    private final long modelVersion;

    public ModelException(String domainModelName, long modelVersion) {
        this.domainModelName = domainModelName;
        this.modelVersion = modelVersion;
    }

    public ModelException(String domainModelName, long modelVersion, String message) {
        super(message);
        this.domainModelName = domainModelName;
        this.modelVersion = modelVersion;
    }

    public ModelException(String domainModelName, long modelVersion, String message, Throwable cause) {
        super(message, cause);
        this.domainModelName = domainModelName;
        this.modelVersion = modelVersion;
    }

    public ModelException(String domainModelName, long modelVersion, Throwable cause) {
        super(cause);
        this.domainModelName = domainModelName;
        this.modelVersion = modelVersion;
    }

    public String getDomainModelName() {
        return domainModelName;
    }

    public long getModelVersion() {
        return modelVersion;
    }

}
