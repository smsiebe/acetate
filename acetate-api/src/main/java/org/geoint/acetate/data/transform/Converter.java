package org.geoint.acetate.data.transform;

/**
 * Converts from one object to another.
 *
 * @param <F> source type (from)
 * @param <T> destination type (to)
 */
public interface Converter<F, T> {

    T convert(F from) throws DataConversionException;

    F invert(T to) throws DataConversionException;
}
