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
     * @param model component model
     * @param reader data as bytes
     * @return data instance
     * @throws DataTransformException thrown if the source could not be read or
     * the object could not be instantiated
     * @throws
     */
    T convert(ModelComponent<T> model, ByteBuffer reader)
            throws DataTransformException;

    /**
     * Converts an object to expected binary format.
     *
     * @param model component model
     * @param data source object
     * @param writer where the bytes are written
     * @throws DataTransformException throws if there are problems writing the
     * object as bytes
     */
    void invert(ModelComponent<T> model, T data, ByteBuffer writer)
            throws DataTransformException;

}
