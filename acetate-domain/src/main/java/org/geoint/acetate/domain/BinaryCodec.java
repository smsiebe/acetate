package org.geoint.acetate.domain;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Converts the value of an object to/from a binary representation.
 * <p>
 * The BinaryCodec does not need to write any metadata to identify the data
 * type.
 * <p>
 * Implementations must the thread-safe.
 *
 * @param <T> class this codec can read/write from bytes
 */
public interface BinaryCodec<T> {

    /**
     * Writes the object value to the channel.
     *
     * @param obj
     * @param channel
     * @throws NonWritableChannelException If this channel was not opened for
     * writing
     * @throws ClosedChannelException If this channel is closed
     * @throws AsynchronousCloseException If another thread closes this channel
     * while the write operation is in progress
     * @throws ClosedByInterruptException If another thread interrupts the
     * current thread while the write operation is in progress, thereby closing
     * the channel and setting the current thread's interrupt status
     * @throws IOException If some other I/O error occurs
     */
    void convert(T obj, WritableByteChannel channel) throws IOException;

    /**
     * Reads the object data from the byte channel, returning the object
     * instance.
     *
     * @param channel
     * @return
     * @throws NonReadableChannelException If this channel was not opened for
     * reading
     * @throws ClosedChannelException If this channel is closed
     * @throws AsynchronousCloseException If another thread closes this channel
     * while the read operation is in progress
     * @throws ClosedByInterruptException If another thread interrupts the
     * current thread while the read operation is in progress, thereby closing
     * the channel and setting the current thread's interrupt status
     * @throws IOException If some other I/O error occurs
     */
    T invert(ReadableByteChannel channel) throws IOException;
}
