package org.geoint.acetate.bind.spi;

import java.io.IOException;

/**
 * Marker interface for the writer for a binder.
 * <p>
 * One of the supported binding writers (binary, string, or object) is returned
 * by the {@link DataBinder}.
 *
 * @see StringDataWriter
 * @see BinaryDataWriter
 * @see TypedDataWriter
 */
public interface BindingWriter {

    /**
     * Hints to the binder that the data to follow is in a data container.
     *
     * @throws IOException thrown if the binder could not write
     */
    void array() throws IOException;
}
