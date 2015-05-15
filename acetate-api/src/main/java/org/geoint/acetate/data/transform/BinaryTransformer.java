package org.geoint.acetate.data.transform;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Transforms binary data to an alternative binary format.
 */
@FunctionalInterface
public interface BinaryTransformer {

    /**
     * Converts a binary data representation from one format to another.
     *
     * @param reader data source
     * @param writer data sink
     * @throws DataConversionException throws if there are any problems reading
     * from or writing two the provided parameters
     */
    void convert(ByteReader reader, ByteWriter writer)
            throws DataConversionException;
}
