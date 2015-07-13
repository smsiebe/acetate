package org.geoint.acetate.data;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Converts data to/from bytes.
 *
 * @param <T>
 */
public interface DataCodec<T> {

    byte[] convert(T obj);

    void convert(T obj, WritableByteChannel channel);

    T invert(byte[] bytes);

    T invert(ReadableByteChannel channel);

    int estimateLength();
}
