package org.geoint.acetate.data.transform;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.io.CharacterReader;
import org.geoint.acetate.io.CharacterWriter;
import org.geoint.acetate.model.DomainObject;

/**
 * Defines a means to convert a {@link DomainObject} to/from a binary and
 * character stream.
 *
 * @param <T> java type which the domain model object is converted between
 */
public interface ObjectCodec<T> {

    /**
     * Convert binary data in expected format to an object instance.
     *
     * @param reader byte source
     * @return data instance
     * @throws DataConversionException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(ByteReader reader) throws DataConversionException;

    /**
     * Converts an object to expected binary format.
     *
     * @param data source object
     * @param writer where the bytes are written
     * @throws DataConversionException throws if there are problems writing the
     * object as bytes
     */
    void invert(T data, ByteWriter writer) throws DataConversionException;

    /**
     * Converts character data in expected format to an object instance.
     *
     * @param reader
     * @return data instance
     * @throws DataConversionException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(CharacterReader reader) throws DataConversionException;

    /**
     * Converts an object to expected character format.
     *
     * @param data
     * @param writer
     * @throws DataConversionException DataConversionException throws if there
     * are problems writing the object as as character string
     */
    void invert(T data, CharacterWriter writer) throws DataConversionException;
}
