package org.geoint.acetate.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public interface CharacterReader extends Closeable, AutoCloseable {

    /**
     * Reads all bytes into memory.
     *
     * @return complete bytes of the data
     * @throws IOException thrown if there are problems reading from the data
     * source
     */
    byte[] read() throws IOException;

    /**
     * Reads 0 or more bytes into the provided ByteBuffer, returning the number
     * of bytes that were read.
     *
     * @param buffer buffer to write the data
     * @return number of bytes read; -1 indicates there is no more bytes
     * @throws IOException thrown if there are problems reading from the data
     * source or the data cannot be written to the buffer
     */
    int read(StringBuffer buffer) throws IOException;

    /**
     * Fills the remaining buffer in the provided buffer.
     *
     * @param buffer destination buffer
     * @throws IOException thrown if there are problems reading
     */
    void readAll(StringBuffer buffer) throws IOException;

    /**
     * Drains the reader to the output stream.
     *
     * @param out stream the reader content is written
     * @throws IOException thrown if there are problems reading from the data
     * source or writing to the output stream
     */
    void drainTo(OutputStream out) throws IOException;
}
