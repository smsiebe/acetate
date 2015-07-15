package org.geoint.acetate.impl.transform;

import java.nio.ByteBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.bind.transform.DataTransformException;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Default date type for {@link Boolean}.
 *
 */
public final class DefaultBooleanBinaryCodec
        implements BufferedCodec<Boolean, ByteBuffer> {

    private static final byte FALSE = (byte) 0;
    private static final byte TRUE = (byte) 1;

    @Override
    public Boolean convert(DataModel<Boolean> model, ByteBuffer reader)
            throws DataTransformException {
        return reader.get() == TRUE;
    }

    @Override
    public void invert(DataModel<Boolean> model, Boolean data, ByteBuffer writer)
            throws DataTransformException {
        writer.put((data) ? TRUE : FALSE);
    }

}
