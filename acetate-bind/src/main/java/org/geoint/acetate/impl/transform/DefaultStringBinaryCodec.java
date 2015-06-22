package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.bind.transform.DataTransformException;
import org.geoint.acetate.model.DataModel;

/**
 * Default data type for {@link String}.
 *
 * Converts the entire content of the ByteReader to a UTF-8 String.
 */
public final class DefaultStringBinaryCodec
        implements BufferedCodec<String, ByteBuffer> {

    @Override
    public String convert(DataModel<String> model, ByteBuffer reader)
            throws DataTransformException {
        return new String(reader.array(), StandardCharsets.UTF_8);
    }

    @Override
    public void invert(DataModel<String> model, String data, ByteBuffer writer)
            throws DataTransformException {
        writer.put(data.getBytes(StandardCharsets.UTF_8));
    }

}
