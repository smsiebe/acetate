package org.geoint.acetate.bind.spi;

import org.geoint.acetate.bind.BindingWriter;
import java.io.IOException;
import org.geoint.acetate.bound.BoundData;

/**
 * Binds typed data to binder.
 */
public interface TypedDataWriter extends BindingWriter {

    /**
     * Writes the bound data to the binder.
     *
     * @param data data to write
     * @throws IOException thrown if there are problems writing the data.
     */
    void write(BoundData data) throws IOException;
}
