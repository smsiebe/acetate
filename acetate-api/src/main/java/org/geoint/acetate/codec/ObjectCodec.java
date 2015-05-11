package org.geoint.acetate.codec;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Converts bytes to objects and objects to bytes.
 *
 * @param <T> data type
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
}
