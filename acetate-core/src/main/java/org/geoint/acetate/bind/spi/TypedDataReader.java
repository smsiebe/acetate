package org.geoint.acetate.bind.spi;

import org.geoint.acetate.bind.BindingReader;
import java.io.EOFException;
import java.io.IOException;
import org.geoint.acetate.bound.BoundField;

/**
 * Returned by {@link DataBinder} implementations that read Object data.
 */
public interface TypedDataReader extends BindingReader {

    /**
     * Returns the next data field.
     *
     * @return bound field
     * @throws IOException thrown if unable to read data
     * @throws EOFException thrown if there is no more data
     */
    BoundField<?> read() throws IOException, EOFException;
}
