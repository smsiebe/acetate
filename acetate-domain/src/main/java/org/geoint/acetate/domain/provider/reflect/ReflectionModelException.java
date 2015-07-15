package org.geoint.acetate.domain.provider.reflect;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if there was a problem (ie security) attempting to generate the
 * metamodel using reflection.
 *
 */
public class ReflectionModelException extends ModelException {

    public ReflectionModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionModelException(Throwable cause) {
        super(cause);
    }

}
