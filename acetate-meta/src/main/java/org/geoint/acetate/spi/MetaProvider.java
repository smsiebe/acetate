package org.geoint.acetate.spi;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;
import org.geoint.acetate.meta.model.MetaModel;

/**
 * Metamodel provider.
 *
 * Metamodel providers are responsible for any caching, if appropriate.
 *
 * MetaProviders are currently only discovered using {@link ServiceLoader}. In
 * the future providers may also be discovered through alternative methods, but
 * ServiceLoader will always be supported.
 *
 * All implementations, and returned meta models, must be atomic and
 * thread-safe.
 */
public interface MetaProvider {

    /**
     * Returns all the meta models found by this provider.
     *
     * @return meta models found by this provider; if no metamodels are found,
     * returns empy collection
     */
    Collection<MetaModel> getModels();

    /**
     * Returns the requested metamodel, if registered.
     *
     * @param modelName
     * @param modelVersion
     * @return metamodel model, if registered
     */
    Optional<MetaModel> getModel(String modelName, String modelVersion);

}
