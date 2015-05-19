
package org.geoint.acetate.io;

/**
 *
 */
public interface CharacterReader extends Closeable, AutoCloseable  {

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
     * @throws BufferOverflowException thrown if the remaining length of the
     * provided buffer is 0 and there are more bytes available to read
     */
    int read(ByteBuffer buffer) throws IOException, BufferOverflowException;

    /**
     * Fills the remaining buffer in the provided buffer.
     *
     * @param buffer destination buffer
     * @throws IOException thrown if there are problems reading
     * @throws BufferOverflowException thrown if there are fewer than
     * buffer.remaining bytes available from the reader
     */
    void readAll(ByteBuffer buffer) throws IOException, BufferOverflowException;

    /**
     * Drains the reader to the output stream.
     *
     * @param out stream the reader content is written
     * @throws IOException thrown if there are problems reading from the data
     * source or writing to the output stream
     */
    void drainTo(OutputStream out) throws IOException;
}
