package org.geoint.acetate;

import java.util.Collection;

/**
 *
 * @param <T> java class representation of the domain type
 */
public interface DomainType<T> extends DomainComponent {

    /**
     * Domain name to use if not defined.
     */
    public static final String DEFAULT_DOMAIN_NAME = "acetate.domain.default";

    /**
     * Default domain version to use if not defined.
     */
    public static final String DEFAULT_DOMAIN_VERSION = "1.0-BETA";

    /**
     * Returns the java class representation of the domain type.
     *
     * @return class representation of this domain type
     */
    Class<T> getTypeClass();

    /**
     * Known subclasses of the DomainType.
     *
     * @return known subclasses
     */
    Collection<DomainType<? extends T>> getSubclasses();

}
