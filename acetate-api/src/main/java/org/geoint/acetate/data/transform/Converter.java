package org.geoint.acetate.data.transform;

/**
 * Converts from one Object to another.
 *
 * Converters may be used in several different ways. One common use us to teach
 * a domain model how to handle objects it encounters which are not "known" to
 * the domain model.
 *
 * All converter implementations must be thread-safe.
 *
 * @param <F> source type (from)
 * @param <T> destination type (to)
 */
public interface Converter<F, T> {

    /**
     * Convert from one object type to another.
     *
     * @param from
     * @return new object (can return null)
     * @throws DataConversionException thrown if there were unexpected problems
     * converting the object
     */
    T convert(F from) throws DataConversionException;

    /**
     * Invert (convert back) the created object type to another.
     *
     * @param to
     * @return reverted/inverted object or null
     * @throws DataConversionException thrown if there were unexpected problems
     * converting the object back to its original form
     */
    F invert(T to) throws DataConversionException;
}
