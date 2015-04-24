package org.geoint.acetate.data;

import java.io.IOException;
import java.util.Optional;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.DataInstantiationException;
import org.geoint.acetate.model.DataType;
import org.geoint.acetate.transform.DataConversionException;

/**
 * A node which contains a data value.
 *
 * @param <T> the value type
 */
public interface DataValueNode<T> extends DataNode {

    /**
     * Attempt to convert the node value to a java object.
     *
     * @return java object or null if the value was null
     * @throws DataConversionException thrown if the binary data could not be
     * converted to the required format for the data type
     * @throws DataInstantiationException thrown if the data could not be
     * instantiated
     */
    Optional<T> asObject()
            throws DataConversionException, DataInstantiationException;

    /**
     * Returns the data type for the node value, if known.
     *
     * @return data type of the node value, if known
     */
    Optional<DataType<T>> getType();

    /**
     * Returns the data value as bytes.
     *
     * @return value as bytes or null
     */
    byte[] asBytes();

    /**
     * Write the data value as bytes to the provided writer.
     *
     * @param writer used to write the bytes
     * @throws IOException thrown if there were problems writing the bytes to
     * the provided writer
     */
    void asBytes(ByteWriter writer) throws IOException;
}
