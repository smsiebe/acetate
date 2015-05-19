package org.geoint.acetate.model.builder;

import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelException;

/**
 * Thrown if a model is incomplete (is missing some components, codecs, etc).
 */
public class IncompleteModelException extends ModelException {

    public IncompleteModelException(DomainModel model) {
        this(model.getName(), model.getVersion());
    }

    public IncompleteModelException(String domainModelName, long modelVersion) {
        super(domainModelName, modelVersion);
    }

    public IncompleteModelException(DomainModel model, String message) {
        this(model.getName(), model.getVersion(), message);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, String message) {
        super(domainModelName, modelVersion, message);
    }

    public IncompleteModelException(DomainModel model, String message, Throwable cause) {
        this(model.getName(), model.getVersion(), message, cause);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, String message, Throwable cause) {
        super(domainModelName, modelVersion, message, cause);
    }

    public IncompleteModelException(String domainModelName, long modelVersion, Throwable cause) {
        super(domainModelName, modelVersion, cause);
    }

}
