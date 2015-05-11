package org.geoint.acetate.model;

import org.geoint.acetate.model.attribute.ComponentAttribute;
import gov.ic.geoint.acetate.AcetateException;

/**
 * Thrown if a data component attribute could not be resolved for a model.
 *
 */
public class InvalidComponentAttributeException extends AcetateException {

    private final String componentPath;
    private final Class<? extends ComponentAttribute> attributeType;

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ComponentAttribute> attributeType) {
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ComponentAttribute> attributeType, String message) {
        super(message);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ComponentAttribute> attributeType, String message,
            Throwable cause) {
        super(message, cause);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ComponentAttribute> attributeType, Throwable cause) {
        super(cause);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public Class<? extends ComponentAttribute> getAttributeType() {
        return attributeType;
    }

}
