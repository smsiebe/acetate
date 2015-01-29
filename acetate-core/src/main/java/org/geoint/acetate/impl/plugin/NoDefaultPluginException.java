package org.geoint.acetate.impl.plugin;

/**
 * Thrown when no default plugin implementation is found for a plugin interface.
 */
public class NoDefaultPluginException extends AcetatePluginException {

    private final Class<?> pluginType;

    public NoDefaultPluginException(Class<?> pluginType) {
        this.pluginType = pluginType;
    }

    public NoDefaultPluginException(Class<?> pluginType, String message) {
        super(message);
        this.pluginType = pluginType;
    }

    public NoDefaultPluginException(Class<?> pluginType, String message, Throwable cause) {
        super(message, cause);
        this.pluginType = pluginType;
    }

    public NoDefaultPluginException(Class<?> pluginType, Throwable cause) {
        super(cause);
        this.pluginType = pluginType;
    }

    /**
     * The plugin interface for which a default implementation could not be
     * found.
     *
     * @return plugin interface type
     */
    public Class<?> getPluginType() {
        return pluginType;
    }

}
