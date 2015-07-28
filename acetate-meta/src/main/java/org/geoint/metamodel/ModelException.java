package org.geoint.metamodel;

/**
 * Top-level exception type for all exceptions thrown by the metamodel
 * framework.
 */
public class ModelException extends Exception {

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
