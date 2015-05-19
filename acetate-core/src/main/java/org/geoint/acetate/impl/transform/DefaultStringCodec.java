package org.geoint.acetate.impl.transform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;

/**
 * Default data type for {@link String}.
 *
 * Converts the entire content of the ByteReader to a UTF-8 String.
 */
public final class DefaultStringCodec extends ComplexObjectCodec<String> {

    public DefaultStringCodec(DomainModel model, ModelContextPath path) {
        super(model, path);
    }

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
