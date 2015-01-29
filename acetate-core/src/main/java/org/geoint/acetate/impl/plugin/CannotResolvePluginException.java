package org.geoint.acetate.impl.plugin;

/**
 * Thrown when acetate cannot figure out which plugin should be used.
 */
public class CannotResolvePluginException extends AcetatePluginException {

    private final Class<?> pluginType;

    public CannotResolvePluginException(Class<?> pluginType) {
        this.pluginType = pluginType;
    }

    public CannotResolvePluginException(Class<?> pluginType, String message) {
        super(message);
        this.pluginType = pluginType;
    }

    public CannotResolvePluginException(Class<?> pluginType, String message, Throwable cause) {
        super(message, cause);
        this.pluginType = pluginType;
    }

    public CannotResolvePluginException(Class<?> pluginType, Throwable cause) {
        super(cause);
        this.pluginType = pluginType;
    }

    /**
     * Plugin interface type attempted to be resolved.
     * 
     * @return plugin interface type 
     */
    public Class<?> getPluginType() {
        return pluginType;
    }
    
    
}
