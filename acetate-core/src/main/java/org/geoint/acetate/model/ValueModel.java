package org.geoint.acetate.model;

import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.transform.DataFormatException;

/**
 * Models a component containing a data value.
 *
 * @param <T> data type of the value
 */
public interface ValueModel<T> extends DataModel<T> {

    /**
     * Converts the content of the buffer to default String format of this value
     * type.
     *
     * @param bb buffer
     * @return default string format of this value type or null if the buffer
     * was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<String> asString(ByteBuffer bb) throws DataFormatException;

    /**
     * Converts the specified content of the buffer to the default String format
     * of this value type.
     *
     * @param bb buffer
     * @param offset start position
     * @param length number of bytes to read from buffer
     * @return default string format of this value type or null if the buffer
     * was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<String> asString(ByteBuffer bb, int offset, int length)
            throws DataFormatException;
}
