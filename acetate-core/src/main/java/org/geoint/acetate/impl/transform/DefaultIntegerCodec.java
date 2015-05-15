package org.geoint.acetate.impl.transform;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.data.transform.ObjectCodec;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;

/**
 * Default data type for {@link Integer}.
 */
public final class DefaultIntegerCodec extends ObjectCodec<Integer> {

    public DefaultIntegerCodec(DomainModel model, ModelContextPath path) {
        super(model, path);
    }

    @Override
    public Integer convert(ByteReader reader) throws DataConversionException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            reader.readAll(buffer);
            return buffer.getInt();
        } catch (IOException ex) {
            throw new DataConversionException("Unable to convert to integer, "
                    + "problems reading from source", ex);
        } catch (BufferOverflowException ex) {
            throw new DataConversionException("Unable to convert to integer, "
                    + "not enough data.");
        }
    }

    @Override
    public void invert(Integer data, ByteWriter writer)
            throws DataConversionException {
        try {
            writer.write(data.byteValue());
        } catch (IOException ex) {
            throw new DataConversionException("Unable to write to Integer bytes",
                    ex);
        }
    }

}
