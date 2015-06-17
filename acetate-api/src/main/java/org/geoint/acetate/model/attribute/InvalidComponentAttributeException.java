package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.AcetateException;

/**
 * Thrown if a data component attribute could not be resolved for a model.
 *
 */
public class InvalidComponentAttributeException extends AcetateException {

    private final String componentPath;
    private final Class<? extends ModelAttribute> attributeType;

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ModelAttribute> attributeType) {
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ModelAttribute> attributeType, String message) {
        super(message);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ModelAttribute> attributeType, String message,
            Throwable cause) {
        super(message, cause);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public InvalidComponentAttributeException(String componentPath,
            Class<? extends ModelAttribute> attributeType, Throwable cause) {
        super(cause);
        this.componentPath = componentPath;
        this.attributeType = attributeType;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public Class<? extends ModelAttribute> getAttributeType() {
        return attributeType;
    }

}
