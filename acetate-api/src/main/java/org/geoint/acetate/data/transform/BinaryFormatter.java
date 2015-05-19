package org.geoint.acetate.data.transform;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Transforms binary data from one binary form to another.
 */
@FunctionalInterface
public interface BinaryFormatter {

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
