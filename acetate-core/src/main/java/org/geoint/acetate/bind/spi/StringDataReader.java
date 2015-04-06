package org.geoint.acetate.bind.spi;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Returned by {@link DataBinder} implementations that read character data.
 */
public interface StringDataReader extends BindingReader {

    /**
     * Reads the next data field.
     *
     * @return next data value (may be null)
     * @throws IOException thrown if there are problems reading the next data
     * field
     * @throws EOFException thrown if there is no more data
     */
    String read() throws IOException, EOFException;

    /**
     * Read the next data value and appends it to the provided Appendable.
     *
     * @param appendable to append data
     * @throws IOException thrown if there are problems reading the data or
     * appending
     * @throws EOFException thrown if there are no more data
     */
    void read(Appendable appendable) throws IOException, EOFException;

    /**
     * Read the next data value and appends it to the provided stream.
     *
     * @param out where to write the data
     * @throws IOException thrown if there are problems reading or writing the
     * data
     * @throws EOFException thrown if there are no more data
     */
    void read(OutputStream out) throws IOException, EOFException;
}
