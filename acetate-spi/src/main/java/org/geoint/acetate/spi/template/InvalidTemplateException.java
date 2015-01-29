package org.geoint.acetate.spi.template;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when the template file content was invalid.
 */
public class InvalidTemplateException extends AcetateException {

    private final String templateName;

    public InvalidTemplateException(String templateName) {
        this.templateName = templateName;
    }

    public InvalidTemplateException(String templateName, String message) {
        super(message);
        this.templateName = templateName;
    }

    public InvalidTemplateException(String templateName, String message, Throwable cause) {
        super(message, cause);
        this.templateName = templateName;
    }

    public InvalidTemplateException(String templateName, Throwable cause) {
        super(cause);
        this.templateName = templateName;
    }

    /**
     * Name of the template that failed parsing.
     *
     * @return name of the template
     */
    public String getTemplateName() {
        return templateName;
    }

}
