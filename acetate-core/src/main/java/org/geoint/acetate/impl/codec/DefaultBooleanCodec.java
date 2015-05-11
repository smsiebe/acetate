package org.geoint.acetate.impl.codec;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.codec.DataConversionException;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.io.ByteReader;

/**
 * Default date type for {@link Boolean}.
 *
 */
public final class DefaultBooleanCodec implements ObjectCodec<Boolean> {

    private static final byte FALSE = (byte) 0;
    private static final byte TRUE = (byte) 1;

    @Override
    public Boolean convert(ByteReader reader) throws DataConversionException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            int read = 0;
            if (reader.read(buffer) == 1) {
                switch (buffer.get()) {
                    case TRUE:
                        return true;
                    case FALSE:
                        return false;
                    default:
                        throw new DataConversionException("Invalid data "
                                + "format for boolean.");
                }
            } else {
                return null;
            }
        } catch (IOException | BufferOverflowException ex) {
            throw new DataConversionException("Problems reading boolean value "
                    + "from ByteReader", ex);
        }
    }

    @Override
    public void invert(Boolean data, ByteWriter writer) throws DataConversionException {
        try {
            if (data == null) {
                return;
            }
            writer.write((data) ? TRUE : FALSE);
        } catch (IOException ex) {
            throw new DataConversionException("Problems writing boolean value "
                    + "to writer.", ex);
        }
    }

}
