package org.geoint.acetate.spi;

import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Metamodel component discovery extension interface.
 * <p>
 * MetaProvider instances are extensions to the acetate framework used to
 * discover all domain model components. MetaProvider instances may be metamodel
 * specific (that is, may be created to findAll only components of a specific
 * metamodel) or may findAll generic model components. Components discovered by
 * a MetaProvider are deduplicated by the metamodel engine.
 * <p>
 * MetaProviders are currently only discovered using {@link ServiceLoader}. In
 * the future providers may also be discovered through alternative methods, but
 * ServiceLoader will always be supported.
 * <p>
 * Metamodel providers are responsible for any caching, if appropriate.
 * <p>
 * All implementations, and returned meta models, must be atomic and
 * thread-safe.
 */
public interface MetaProvider {

    /**
     * Returns all the object models found by this provider.
     *
     * @return meta models found by this provider; if no metamodels are found,
     * returns empty collection
     */
    Collection<ObjectModel> findAll();

    /**
     * Return all domain components for the specified domain model found by this
     * provider.
     *
     * @param domainName
     * @param domainVersion
     * @return domain model components found by this provider
     */
    Collection<ObjectModel> find(String domainName, MetaVersion domainVersion);
}
