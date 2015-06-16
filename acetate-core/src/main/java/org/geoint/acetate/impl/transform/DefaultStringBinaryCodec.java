package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.DataTransformException;

/**
 * Default data type for {@link String}.
 *
 * Converts the entire content of the ByteReader to a UTF-8 String.
 */
public final class DefaultStringBinaryCodec implements BinaryCodec<String> {

    @Override
    public String convert(ByteBuffer reader)
            throws DataTransformException {
        return new String(reader.array(), StandardCharsets.UTF_8);
    }

    @Override
    public void invert(String data, ByteBuffer writer)
            throws DataTransformException {
        writer.put(data.getBytes(StandardCharsets.UTF_8));
    }

}
