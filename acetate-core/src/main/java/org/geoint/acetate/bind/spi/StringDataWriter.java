package org.geoint.acetate.bind.spi;

import java.io.IOException;

/**
 * Returned by a {@link DataBinder} that supports character-based data.
 */
public interface StringDataWriter extends BindingWriter {

    /**
     * Write the data to the binder.
     *
     * @param path path to the data
     * @param data data value, may be null
     * @throws IOException thrown if there are problems writing
     */
    void write(String path, String data) throws IOException;
}
