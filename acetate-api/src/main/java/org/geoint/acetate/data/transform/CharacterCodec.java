package org.geoint.acetate.data.transform;

import java.nio.CharBuffer;

/**
 * Converts a domain model object instance to/from a character stream.
 *
 * All codec implementations must be thread-safe.
 *
 * @param <T> java type which the domain model object is converted between
 */
public interface CharacterCodec<T> {

    /**
     * Converts character data in expected format to an object instance.
     *
     * @param reader
     * @return data instance
     * @throws DataConversionException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(CharBuffer reader) throws DataConversionException;

    /**
     * Converts an object to expected character format.
     *
     * @param data
     * @param writer
     * @throws DataConversionException DataConversionException throws if there
     * are problems writing the object as as character string
     */
    void invert(T data, CharBuffer writer) throws DataConversionException;
}
