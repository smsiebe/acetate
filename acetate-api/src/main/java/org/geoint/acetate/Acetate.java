package org.geoint.acetate;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.metamodel.ModelException;
import org.geoint.acetate.metamodel.MutableModel;
import org.geoint.acetate.spi.model.ModelProvider;
import org.geoint.acetate.spi.model.NoModelProviderException;

/**
 * Simple user interface for most data binding operations.
 */
public final class Acetate {

    private static ModelProvider defaultModelProvider;
    private static final Logger logger = Logger.getLogger("org.geoint.acetate");

    public synchronized static void setDefaultModelProvider(ModelProvider mp) {
        defaultModelProvider = mp;
    }

    /**
     * Uses the default model provider to create a base data model from which
     * changes can be made programmatically.
     *
     * @param <T> root class type of the model
     * @param clazz root class of the model
     * @return mutable data model
     * @throws ModelException if the model could not be created
     */
    public static <T> MutableModel<T> mutableModel(Class<T> clazz)
            throws ModelException {
        ModelProvider provider = provider();
        if (provider == null) {
            throw new NoModelProviderException(clazz);
        }
        return provider.mutableModel(clazz);
    }

    private static ModelProvider provider() {
        if (defaultModelProvider == null) {
            try {
                Class<ModelProvider> defaultProviderClass
                        = (Class<ModelProvider>) Class.forName("org.geoint.acetate.impl.model.reflect.ReflectionModelProvider");
                defaultModelProvider = defaultProviderClass.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                logger.log(Level.SEVERE, "Default model provider was not set and "
                        + "was unable to load default model provider.  Please set "
                        + "a default model provider to be sure to include "
                        + "acetate-core on the classpath.", ex);
            }
        }
        return defaultModelProvider;
    }

}
