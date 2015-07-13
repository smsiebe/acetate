package org.geoint.acetate.spi;

import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelVersion;

/**
 * Model discovery extension interface.
 * <p>
 * ModelProvider instances are extensions to the acetate framework used to
 * discover all domain model components. Components discovered by a
 * ModelProvider are deduplicated by the taxonomy.
 * <p>
 * ModelProvider instances are currently only discovered using
 * {@link ServiceLoader}. In the future providers may also be discovered through
 * alternative methods, but ServiceLoader will always be supported.
 * <p>
 * All implementations, and returned meta models, must be atomic and
 * thread-safe.
 */
public interface ModelProvider {

    /**
     * Returns all the object models found by this provider.
     *
     * @return meta models found by this provider; if no metamodels are found,
     * returns empty collection
     */
    Collection<DomainModel> findAll();

    /**
     * Return all domain components for the specified domain model found by this
     * provider.
     *
     * @param domainName
     * @param domainVersion
     * @return domain model components found by this provider
     */
    Collection<DomainModel> find(String domainName, ModelVersion domainVersion);
}
