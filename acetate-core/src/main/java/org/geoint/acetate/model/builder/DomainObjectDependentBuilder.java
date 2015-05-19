package org.geoint.acetate.model.builder;

import java.util.Set;

/**
 * Interface for builders which depend on DomainObject definitions.
 */
public interface DomainObjectDependentBuilder {

    /**
     * Returns the domain object names the builder, in its current state,
     * depends on. Builders containing other builders must check recursive.
     *
     * @return
     */
    Set<String> getDependencies();

}
