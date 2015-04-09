package org.geoint.acetate.bind.spi;

import org.geoint.acetate.bind.BindingReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Returned by {@link DataBinder} implementations that read binary data.
 */
public interface BinaryDataReader extends BindingReader {

    /**
     * Reads the next data.
     *
     * @return field data
     * @throws IOException thrown if there are problems reading or writing the
     * data
     * @throws EOFException thrown if there are no more data
     */
    byte[] read() throws IOException, EOFException;

    /**
     * Reads the next data into the provided buffer.
     *
     * @param buffer
     * @throws IOException thrown if there are problems reading or writing the
     * data to the buffer
     * @throws EOFException thrown if there are no more data
     */
    void read(ByteBuffer buffer) throws IOException, EOFException;

    /**
     * Reads the next data into the provided output stream.
     *
     * @param out
     * @throws IOException thrown if there are problems reading or writing the
     * data
     * @throws EOFException thrown if there are no more data
     */
    void read(OutputStream out) throws IOException, EOFException;
}
