package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ModelException;

/**
 * Thrown if a model is incomplete (is missing some components, codecs, etc).
 */
public class IncompleteModelException extends ModelException {

    public IncompleteModelException(String domainModelName, long modelVersion) {
        super(domainModelName, modelVersion);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, String message) {
        super(domainModelName, modelVersion, message);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, String message, Throwable cause) {
        super(domainModelName, modelVersion, message, cause);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, Throwable cause) {
        super(domainModelName, modelVersion, cause);
    }

}
