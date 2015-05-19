package org.geoint.acetate.data.transform;

import java.nio.ByteBuffer;

/**
 * Codec used to convert a domain model object instance to/from a binary stream.
 *
 * All codec implementations must be thread-safe.
 *
 * @param <T> 
 */
public interface BinaryCodec<T> {

    /**
     * Convert binary data in expected format to an object instance.
     *
     * @param reader byte source
     * @return data instance
     * @throws DataConversionException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(ByteBuffer reader) throws DataConversionException;

    /**
     * Converts an object to expected binary format.
     *
     * @param data source object
     * @param writer where the bytes are written
     * @throws DataConversionException throws if there are problems writing the
     * object as bytes
     */
    void invert(T data, ByteBuffer writer) throws DataConversionException;

}
