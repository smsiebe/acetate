package org.geoint.acetate.spi.model;

import org.geoint.acetate.metamodel.ModelException;

/**
 * Thrown when a model provider could not be found.
 */
public final class NoModelProviderException extends ModelException {

    private static final String error = "Unable to find model provider";

    public NoModelProviderException(Class<?> modelType) {
        super(modelType, error);
    }

    public NoModelProviderException(Class<?> modelType, Throwable cause) {
        super(modelType, error, cause);
    }

}
