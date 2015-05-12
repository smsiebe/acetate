package org.geoint.acetate.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Simple byte writer abstraction used by Acetate to support different binary
 * data outputs.
 */
public interface ByteWriter extends Flushable, Closeable, AutoCloseable {

    /**
     * Writes the content of the byte array.
     *
     * @param bytes data
     * @throws IOException thrown if there are problems writing the data
     */
    void write(byte... bytes) throws IOException;

    /**
     * Writes the content of the buffer.
     *
     * @param buffer data
     * @throws IOException thrown if there are problems writing the data
     */
    void write(ByteBuffer buffer) throws IOException;

    /**
     * Writes the defined buffer content.
     *
     * @param buffer data
     * @param length number of bytes to read from the buffer
     * @throws BufferUnderflowException thrown if there the number of bytes
     * remaining in the buffer is less than the provided length
     * @throws IOException thrown if there are problems writing the data
     */
    void write(ByteBuffer buffer, int length)
            throws IOException, BufferUnderflowException;
}
