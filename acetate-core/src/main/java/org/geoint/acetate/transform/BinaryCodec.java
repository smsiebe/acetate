package org.geoint.acetate.transform;

import java.nio.ByteBuffer;

/**
 * Converts binary data from one (binary) format to another (binary format).
 *
 * A BinaryCodec is optionally used when defining a {@link DataStructure}
 */
public interface BinaryCodec {

    /**
     * Returns a binary data converter intended to convert the data encountered
     * in a {@link DataStructure} to the binary format expected by the
     * {@link DataType} of the {@link Data Model} field.
     *
     * @param in binary data from the data structure
     * @param offset data read offset
     * @param length data length
     * @return binary converter
     */
    BinaryConverter converter(ByteBuffer in, int offset, int length);

    /**
     * Returns the binary data converter intended to convert the binary data
     * returned by the {@link DataType} of the model component to the binary
     * format required by the {@link DataStructure}.
     *
     * @param in binary data from the DataType#asBinary
     * @param offset data read offset
     * @param length data length
     * @return binary inverter
     */
    BinaryConverter invert(ByteBuffer in, int offset, int length);
}
