package org.geoint.acetate.impl.codec;

import java.io.IOException;
import org.geoint.acetate.io.ByteWriter;

/**
 * Default data type for {@link Integer}.
 */
public final class DefaultIntegerType implements DataType<Integer> {

    @Override
    public Class<Integer> getTypeClass() {
        return Integer.class;
    }

    @Override
    public void toBytes(Integer instance, ByteWriter writer) throws IOException {
        writer.write(instance.byteValue());
    }

}
