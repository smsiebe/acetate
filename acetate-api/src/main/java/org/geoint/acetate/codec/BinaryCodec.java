package org.geoint.acetate.codec;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Converts binary data from one (binary) format to another (binary format).
 *
 * A BinaryCodec is optionally used when defining a {@link DataStructure}
 */
public interface BinaryCodec {

    /**
     * Returns a binary data converter used to convert binary data from one
     * format to another.
     *
     * @param reader binary source
     * @param writer binary sink
     * @throws DataConversionException throws if there are any problems reading
     * from or writing two the provided parameters
     */
    void convert(ByteReader reader, ByteWriter writer)
            throws DataConversionException;

    /**
     * Returns a binary data converter used to convert binary data from one
     * format to another.
     *
     * @param reader binary source
     * @param writer binary sink
     * @throws DataConversionException throws if there are any problems reading
     * from or writing two the provided parameters
     */
    void invert(ByteReader reader, ByteWriter writer)
            throws DataConversionException;
}
