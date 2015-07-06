package org.geoint.acetate.impl.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.impl.domain.model.DomainId;
import org.geoint.acetate.impl.domain.model.ImmutableDomainModel;
import org.geoint.acetate.impl.domain.model.UnknownDomainObjectException;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.spi.MetaProvider;

/**
 * Loads {@link MetaProvider meta providers}.
 */
public abstract class MetaModelProviders {

    private static final Collection<MetaProvider> providers;
    private static final Map<DomainId, DomainModel> domains = new HashMap<>();

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
     * Returns all the object meta models from all providers.
     *
     * @return meta models found by all providers
     */
    public static Set<ObjectModel> getModels() {
        return getProviders().parallelStream()
                .flatMap((p) -> p.findAll().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the requested domain model.
     *
     * @param domainName
     * @param domainVersion
     * @return domain model, if registered
     */
    public static Optional<DomainModel> getDomain(String domainName,
            MetaVersion domainVersion) {

        final DomainId domainId = DomainId.getInstance(domainName, domainVersion);

        synchronized (domainId) {
            if (!domains.containsKey(domainId)) {
                Collection<ObjectModel> domainObjects
                        = getProviders().parallelStream()
                        .map((p) -> p.find(domainName, domainVersion))
                        .flatMap((o) -> o.stream())
                        .collect(Collectors.toList());
                if (domainObjects.isEmpty()) {
                    domains.put(domainId, null);
                } else {
                    try {
                        domains.put(domainId,
                                new ImmutableDomainModel(domainId, domainObjects));
                    } catch (UnknownDomainObjectException ex) {
                        domains.put(domainId, null);
                        logger.log(Level.SEVERE, "Unable to retrieve domain "
                                + "model, problems modeling domain.", ex);
                    }
                }
            }
        }
        return Optional.ofNullable(domains.get(domainId));
    }
}
