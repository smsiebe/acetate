package org.geoint.acetate.model;

import java.io.IOException;
import org.geoint.acetate.io.ByteWriter;

/**
 * A data type defines the java class representation of a data component in the
 * model.
 *
 * All DataType instances must be thread-safe.
 *
 * @param <T> java object representation of the data
 * @see BinaryDataType
 * @see ComposedDataType
 */
public interface DataType<T> {

    /**
     * The java {@link Class} this data type supports.
     *
     * @return
     */
    Class<T> getTypeClass();

    /**
     * Writes the java object instance as bytes.
     *
     * @param instance data
     * @param writer where the bytes are written
     * @throws IOException thrown if there are problems writing the bytes
     */
    void toBytes(T instance, ByteWriter writer) throws IOException;
}
