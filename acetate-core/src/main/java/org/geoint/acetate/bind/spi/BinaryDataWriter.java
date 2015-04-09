package org.geoint.acetate.bind.spi;

import org.geoint.acetate.bind.BindingWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Returned by a {@link DataBinder} that supports binary data.
 */
public interface BinaryDataWriter extends BindingWriter {

    /**
     * Writes the data to the specified path.
     *
     * @param path data location
     * @param data data
     * @throws IOException thrown if there are problems writing data
     */
    void write(String path, byte[] data) throws IOException;

    /**
     * Writes the content of the buffer to the specified path.
     *
     * @param path data path
     * @param buffer data
     * @throws IOException thrown if there are problems writing data
     */
    void write(String path, ByteBuffer buffer) throws IOException;

    /**
     * Writes the specified content of the buffer to the specified path.
     *
     * @param path data path
     * @param buffer data
     * @param offset start location in buffer (inclusive)
     * @param length number of bytes to read from the buffer
     * @throws IOException thrown if there are problems writing data
     */
    void write(String path, ByteBuffer buffer, int offset, int length)
            throws IOException;
}
