package org.geoint.acetate.domain;

import java.util.Collection;
import org.geoint.acetate.data.DataCodec;

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
     * Domain model unique type name.
     *
     * @return name of the domain type
     */
    String getName();

    /**
     * Returns the java class representation of the domain type.
     *
     * @return class representation of this domain type
     */
    Class<T> getTypeClass();

    Collection<DomainType<? extends T>> getSubclasses();

    DataCodec<T> getDefaultCodec();

}
