package org.geoint.acetate.domain;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.impl.domain.MetaModelProviders;
import org.geoint.acetate.impl.domain.model.DomainBuilder;
import org.geoint.acetate.impl.domain.model.DomainId;
import org.geoint.acetate.model.ModelVersion;
import org.geoint.acetate.spi.ModelProvider;

/**
 * Domain model registry.
 *
 * The DomainRegistry is the recommended entry point for applications working
 * with domain models.
 * <p>
 * Frameworks creating their own, or extending, the metamodel framework may also
 * find the {@link org.geoint.acetate.api.* API} and
 * {@link org.geoint.acetate.spi.* SPI} packages useful.
 * <p>
 * DomainRegistry is thread-safe.
 */
public final class DomainRegistry {

    private static final DomainRegistry DEFAULT = defaultRegistry();
    private static final Logger logger
            = Logger.getLogger(DomainRegistry.class.getName());

    private final Map<DomainId, DomainModel> domainCache
            = new ConcurrentHashMap<>();
    private final ExecutorService searchExecutor;

    /**
     * Return the default domain registry instance.
     *
     * @return default domain registry instance
     */
    public static DomainRegistry getDefault() {
        return DEFAULT;
    }

    private static DomainRegistry defaultRegistry() {
        Collection<ModelProvider> providers = MetaModelProviders.getProviders();
        ExecutorService exe = Executors.newCachedThreadPool();
        final DomainBuilder db = new DomainBuilder();
        providers.stream().forEach((p) -> {
            exe.execute(() -> p.findAll()
                    .forEach((o) -> db.withObject(o))
            );
        });
        exe.shutdown();
        return new DomainRegistry(exe);
    }

    private DomainRegistry(ExecutorService searchExecutor) {
        this.searchExecutor = searchExecutor;
    }

    public Optional<DomainModel> findDomain(String domainName,
            ModelVersion domainVersion) {
        final DomainId domainId = DomainId.getInstance(domainName, domainVersion);
        try {
            if (searchExecutor != null
                    && searchExecutor.awaitTermination(2, TimeUnit.MINUTES)) {
                return Optional.ofNullable(domainCache.get(domainId));
            }
            logger.info(() -> "Searching for domain model components is taking "
                    + "a long time (> 2min), findDomain request has timedout.");
        } catch (InterruptedException ex) {
            if (Thread.interrupted()) {
                logger.log(Level.INFO, "Interrupted while searching for domain model "
                        + "components.", ex);
            }
        }
        return Optional.empty();
    }

}
