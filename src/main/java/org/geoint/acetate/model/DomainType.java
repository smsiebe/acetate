package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.data.DataCodec;

/**
 *
 */
public interface DomainType<T> {

    String getDomainName();

    String getDomainVersion();

    Collection<DomainType<? extends T>> getSubclasses();

    DataCodec<T> getDefaultCodec();

}
