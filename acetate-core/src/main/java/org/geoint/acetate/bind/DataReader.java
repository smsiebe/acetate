package org.geoint.acetate.bind;

import java.io.IOException;
import java.util.Optional;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Data reader.
 */
public interface DataReader {

    /**
     * Advances the position of the reader on the data, returning the structural
     * type of the new (current) position.
     *
     * @return structural component type
     * @throws DataBindException
     */
    DataStructureType next() throws DataBindException;

    /**
     * Current data position of the reader.
     *
     * @return reader data position
     * @throws DataBindException
     */
    Optional<String> position() throws DataBindException;

    /**
     * Returns a byte reader if the component has a value.
     *
     * @return reader if the component has a value
     */
    Optional<ByteReader> read();

    /**
     * Writes the value to the provided writer, if the component has a value.
     *
     * @param writer byte writer
     * @throws IOException thrown if there was a value but there was a problem
     * writing the value
     */
    void writeBytes(ByteWriter writer) throws IOException;
}
