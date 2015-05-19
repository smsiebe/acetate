package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.DataConversionException;

/**
 * Default data type for {@link Integer}.
 */
public final class DefaultIntegerBinaryCodec implements BinaryCodec<Integer> {

    @Override
    public Integer convert(ByteBuffer reader) throws DataConversionException {
        return reader.getInt();
    }

    @Override
    public void invert(Integer data, ByteBuffer writer)
            throws DataConversionException {
        writer.putInt(data);
    }

}
