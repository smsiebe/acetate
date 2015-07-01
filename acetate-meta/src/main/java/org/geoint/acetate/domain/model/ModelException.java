package org.geoint.acetate.meta;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if there was a problem with a metamodel.
 */
public abstract class ModelException extends AcetateException {

    public ModelException() {
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

}
