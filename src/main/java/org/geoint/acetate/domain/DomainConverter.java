package org.geoint.acetate.domain;

/**
 * Converts an object to/from a {@link DomainType}.
 *
 * @param <F> potentially non-domain type
 * @param <T> domain type
 */
public interface DomainConverter<F, T> {

    T convert(F obj);

    F invert(T domainObj);

}
