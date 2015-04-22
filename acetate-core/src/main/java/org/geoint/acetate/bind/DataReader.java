package org.geoint.acetate.bind;

import org.geoint.acetate.structure.StructureType;
import java.nio.ByteBuffer;
import java.util.Optional;

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
    StructureType next() throws DataBindException;

    /**
     * Current data position of the reader.
     *
     * @return reader data position
     * @throws DataBindException
     */
    Optional<String> position() throws DataBindException;

    /**
     * Length, in bytes, of the data value.
     *
     * @return byte length of the currently data model
     * @throws DataBindException
     */
    int length() throws DataBindException;

    /**
     * Read the value of the data the reader is currently pointing to, writing
     * the value to the provided buffer.
     *
     * A positive integer returned from this method will result in the number of
     * {@link #remaining} bytes for this component to be decremented by the same
     * value.
     *
     * @param buffer buffer to write the value data
     * @return number of bytes read into the buffer, -1 if no data is available
     * @throws DataBindException if there were problems reading/binding the
     * value
     */
    int read(ByteBuffer buffer) throws DataBindException;

    /**
     * Number of remaining bytes for the value of this data component.
     *
     * @return number of remaining bytes of the value
     * @throws DataBindException
     */
    int remaining() throws DataBindException;

    /**
     * Returns a {@link ByteBuffer} containing the data value.
     *
     * @return buffer of value data
     * @throws DataBindException
     */
    ByteBuffer read() throws DataBindException;
}
