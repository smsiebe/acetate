package org.geoint.acetate.data.transform;

import java.nio.CharBuffer;

/**
 * Converts a domain model component instance to/from character data.
 *
 * All codec implementations must be thread-safe.
 *
 * @param <T> java type which the domain model object is converted between
 */
public interface CharacterCodec<T> {

    /**
     * Converts character data to a domain model component instance.
     *
     * @param reader
     * @return data instance
     * @throws DataTransformException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(ModelComponent<T> model, CharBuffer reader) throws DataTransformException;

    /**
     * Converts an object to expected character format.
     *
     * @param model component model
     * @param data domain component instance
     * @param writer 
     * @throws DataTransformException DataTransformException throws if there are
     * problems writing the object as as character string
     */
    void invert(ModelComponent<T> model, T data, CharBuffer writer) throws DataTransformException;
}
