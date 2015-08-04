package org.geoint.acetate.domain;

import java.util.Collection;
import org.geoint.acetate.data.DataCodec;

/**
 *
 * @param <T> java class representation of the domain type
 */
public interface DomainType<T> {

    String getDomainName();

    String getDomainVersion();

    Collection<DomainType<? extends T>> getSubclasses();

    DataCodec<T> getDefaultCodec();

}
