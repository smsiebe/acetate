package org.geoint.acetate.impl.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.codec.DataConversionException;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.io.ByteReader;

/**
 * Default data type for {@link String}.
 *
 * Converts the entire content of the ByteReader to a UTF-8 String.
 */
public final class DefaultStringCodec implements ObjectCodec<String> {

    @Override
    public String convert(ByteReader reader) throws DataConversionException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            reader.drainTo(bout);
            return new String(bout.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new DataConversionException("Problems converting bytes to "
                    + "String", ex);
        }
    }

    @Override
    public void invert(String data, ByteWriter writer)
            throws DataConversionException {
        try {
            writer.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new DataConversionException("Problems converting String to "
                    + "bytes", ex);
        }
    }

}
