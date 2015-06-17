package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.bind.transform.DataTransformException;

/**
 * Default date type for {@link Boolean}.
 *
 */
public final class DefaultBooleanBinaryCodec implements BufferedCodec<Boolean> {

    private static final byte FALSE = (byte) 0;
    private static final byte TRUE = (byte) 1;

    @Override
    public Boolean convert(ByteBuffer reader) throws DataTransformException {
        return reader.get() == TRUE;
    }

    @Override
    public void invert(Boolean data, ByteBuffer writer)
            throws DataTransformException {
        writer.put((data) ? TRUE : FALSE);
    }

}
