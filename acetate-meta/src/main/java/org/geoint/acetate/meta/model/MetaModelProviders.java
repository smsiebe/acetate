package org.geoint.acetate.meta.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.spi.MetaProvider;

/**
 * Loads {@link MetaProvider meta providers}.
 */
public abstract class MetaModelProviders {

    private static final Collection<MetaProvider> providers;

    private static final Logger logger
            = Logger.getLogger(MetaModelProviders.class.getName());

    static {
        logger.info(() -> "Loading metamodel providers from ServiceLoader.");
        final ServiceLoader<MetaProvider> providerLoader
                = ServiceLoader.load(MetaProvider.class);

        Collection<MetaProvider> loaded = new ArrayList<>();
        for (MetaProvider p : providerLoader) {
            logger.fine(() -> "Found metamodel provider '"
                    + p.getClass().getName() + "'.");
            loaded.add(p);
        }
        providers = Collections.unmodifiableCollection(loaded);
    }

    /**
     * Metamodel providers.
     *
     * @return collection of metamodel providers
     */
    public static Collection<MetaProvider> getProviders() {
        return providers;
    }

    /**
     * Returns all the meta models from all providers.
     *
     * @return meta models found by all providers
     */
    public static Set<MetaModel> getModels() {
        return getProviders().parallelStream()
                .flatMap((p) -> p.getModels().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the requested metamodel, searching each provider until the first
     * metamodel instance is found.
     *
     * @param modelName
     * @param modelVersion
     * @return metamodel model, if registered
     */
    public static Optional<MetaModel> getModel(String modelName,
            String modelVersion) {
        return getProviders().parallelStream()
                .map((p) -> p.getModel(modelName, modelVersion))
                .filter((o) -> o.isPresent())
                .map((o) -> o.get())
                .findFirst();
    }
}
