package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.bind.transform.DataTransformException;
import org.geoint.acetate.model.DataModel;

/**
 * Default data type for {@link Integer}.
 */
public final class DefaultIntegerBinaryCodec implements BufferedCodec<Integer, ByteBuffer> {

    @Override
    public Integer convert(DataModel<Integer> model, ByteBuffer reader)
            throws DataTransformException {
        return reader.getInt();
    }

    @Override
    public void invert(DataModel<Integer> model, Integer data, ByteBuffer writer)
            throws DataTransformException {
        writer.putInt(data);
    }

}
