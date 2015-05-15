package org.geoint.acetate.data.transform;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ModelContextPath;

/**
 * Domain model registry for object converters.
 */
public interface ConverterRegistry {

    Collection<Converter<?, ?>> findAll();

    <F> Collection<Converter<F, ?>> find(Class<F> f);

    <F> Optional<Converter<F, ?>> findDefault(Class<F> f);

    <F, T> Collection<Converter<F, T>> find(Class<F> f, Class<T> t);

    /**
     * Find the convert that must be used for the provided domain model
     * component.
     *
     * @param context
     * @return
     */
    Optional<Converter<?, ?>> find(ModelContextPath context);

}
