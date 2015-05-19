
package org.geoint.acetate.io;

/**
 * Writer of character data
 */
public interface CharacterWriter extends Flushable, Closeable, AutoCloseable {

    /**
     * Writes the content of the byte array.
     *
     * @param data data as String
     * @throws IOException thrown if there are problems writing the data
     */
    void write(String data) throws IOException;

    /**
     * Writes the content of the buffer.
     *
     * @param buffer data
     * @throws IOException thrown if there are problems writing the data
     */
    void write(StringBuffer buffer) throws IOException;

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
