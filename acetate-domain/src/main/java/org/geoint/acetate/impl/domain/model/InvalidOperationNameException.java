package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.meta.ModelException;

/**
 * Thrown if the operation name was invalid.
 */
public class InvalidOperationNameException extends ModelException {

    private final String invalidName;

    public InvalidOperationNameException(String invalidName) {
        this(invalidName, getDefaultMessage(invalidName));
    }

    public InvalidOperationNameException(String invalidName, String message) {
        super(message);
        this.invalidName = invalidName;
    }

    public InvalidOperationNameException(String invalidName, Throwable cause) {
        super(getDefaultMessage(invalidName), cause);
        this.invalidName = invalidName;
    }

    public InvalidOperationNameException(String invalidName, String message,
            Throwable cause) {
        super(message, cause);
        this.invalidName = invalidName;
    }

    public String getInvalidName() {
        return invalidName;
    }

    private static String getDefaultMessage(String invalidName) {
        return "Invalid model operation name '" + invalidName + "'";
    }

}
