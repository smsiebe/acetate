package org.geoint.acetate.io;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Simple byte reader abstraction used by Acetate to support different binary
 * data sources.
 */
public interface ByteReader {

    /**
     * Reads all bytes into memory.
     *
     * @return complete bytes of the data
     * @throws IOException thrown if there are problems reading from the data
     * source
     */
    byte[] read() throws IOException;

    /**
     * Reads 0 or more bytes into the provided ByteBuffer.
     *
     * @param buffer buffer to write the data
     * @return number of bytes read; -1 indicates there is no more bytes
     * @throws IOException thrown if there are problems reading from the data
     * source or the data cannot be written to the buffer
     * @throws BufferOverflowException thrown if the remaining length of the
     * provided buffer is 0 and there are more bytes available to read
     */
    int read(ByteBuffer buffer) throws IOException, BufferOverflowException;
}
