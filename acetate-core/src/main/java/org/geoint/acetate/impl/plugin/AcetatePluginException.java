package org.geoint.acetate.impl.plugin;

import org.geoint.acetate.AcetateRuntimeException;

/**
 * Base exception type thrown when there are problems loading or using an 
 * acetate plugin.
 */
public abstract class AcetatePluginException extends AcetateRuntimeException {

    public AcetatePluginException() {
    }

    public AcetatePluginException(String message) {
        super(message);
    }

    public AcetatePluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcetatePluginException(Throwable cause) {
        super(cause);
    }

    
}
