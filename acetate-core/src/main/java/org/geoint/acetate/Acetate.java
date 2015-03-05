package org.geoint.acetate;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.metamodel.ModelException;
import org.geoint.acetate.metamodel.MutableModel;
import org.geoint.acetate.spi.model.ModelProvider;
import org.geoint.acetate.spi.model.NoModelProviderException;

/**
 * Convenience interface for most data binding operations.
 *
 * Ideally applications will only depend (maven scope provided) on the
 * acetate-api library and simply add optional plugins on the classpath at
 * deployment time. However, there are times when an application will want to
 * programmatically bind data and the Acetate class provides the convenience
 * methods necessary to do most tasks.
 *
 */
public final class Acetate {

    private static ModelProvider defaultModelProvider;
    private static final Logger logger = Logger.getLogger("org.geoint.acetate");
    public static final String PROPERTY_DEFAULT_PROVIDER_CLASS
            = "acetate.defaultProvider.class";
    public static final String REFLECTION_PROVIDER
            = "org.geoint.acetate.reflect.ReflectionModelProvider";

    public synchronized static void setDefaultModelProvider(ModelProvider mp) {
        defaultModelProvider = mp;
    }

    /**
     * Uses the default model provider to create a base data model from a class,
     * returning a MutableModel with which changes can be made programmatically.
     *
     *
     * @param <T> root class type of the model
     * @param clazz root class of the model
     * @return mutable data model
     * @throws ModelException if the model could not be created
     */
    public static <T> MutableModel<T> mutableModel(Class<T> clazz)
            throws ModelException {
        ModelProvider provider = defaultProvider();
        if (provider == null) {
            throw new NoModelProviderException(clazz);
        }
        return provider.mutableModel(clazz);
    }

    /**
     * Discovers the default ModelProvider if not already set.
     *
     * The default model provider is resolved when any of the following steps
     * produces a ModelProvider instance:
     * <ol>
     * <li>The default model provider has previously been set from 
     * {@link #setDefaultModelProvider(ModelProvider) }</li>
     * <li>A subclass of ModelProvider is available on the classpath and is
     * defined by the <i>acetate.defaultProvider.class</i> JVM property.</li>
     * <li>ServiceLoader discovers a single ModelProvider implementation on the
     * classpath.</li>
     * <li>The org.geoint.acetate.impl.model.reflect.ReflectionModelProvider
     * instance is found on the classpath.</li>
     * </ol>
     *
     * @return default ModelProvider
     * @throws NoModelProviderException if a default ModelProvider could not be
     * determined
     */
    private static ModelProvider defaultProvider() throws NoModelProviderException {
        if (defaultModelProvider != null) {
            return defaultModelProvider;
        }

        //attempt to load from properties
        if (System.getProperty(PROPERTY_DEFAULT_PROVIDER_CLASS) != null) {
            defaultModelProvider
                    = loadModelProvider(System.getProperty(PROPERTY_DEFAULT_PROVIDER_CLASS));
            if (defaultModelProvider == null) {
                logger.log(Level.FINER, "Acetate ModelProvider was not specified by "
                        + "JVM property '" + PROPERTY_DEFAULT_PROVIDER_CLASS + "'.");
            } else {
                return defaultModelProvider;
            }
        }

        //attempt to load from ServiceLoader
        ServiceLoader<ModelProvider> providerLoader = ServiceLoader.load(ModelProvider.class);
        Iterator<ModelProvider> providers = providerLoader.iterator();
        if (providers.hasNext()) {
            final ModelProvider provider = providers.next();
            if (!providers.hasNext()) {
                defaultModelProvider = provider;
                return provider;
            } else {
                logger.log(Level.FINER, "Acetate default ModelProvider was not "
                        + "loadable from ServiceLoader since more than one "
                        + "implementation was found.");
            }
        } else {
            logger.log(Level.FINER, "Acetate default ModelProvider was not "
                    + "loadable from ServiceLoader; no ModelProvider implementation "
                    + "was found on the classpath.");
        }

        //all else fails, attempt to load the reflection model provider
        defaultModelProvider = loadModelProvider(REFLECTION_PROVIDER);
        return defaultModelProvider;
    }

    private static ModelProvider loadModelProvider(String providerClassName) {

        try {
            Class<ModelProvider> defaultProviderClass
                    = (Class<ModelProvider>) Class.forName(providerClassName);
            return defaultProviderClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.log(Level.FINER, "ModelProvider '" + providerClassName
                    + "' was not found on classpath.", ex);
        }

        return null;
    }
}
