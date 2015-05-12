package org.geoint.acetate.model.registry;

import org.geoint.acetate.model.DomainModel;

/**
 * Model registry for domain models, their components, and acetate layers
 * defined against them.
 *
 * All operations on ModelRegistry implementations must be atomic and
 * thread-safe.
 */
public interface ModelRegistry {

    boolean isRegistered(String domainModelName, long version);

    DomainModel getModel(String domainModelName, long version);

    void register(DomainModel model);

}
