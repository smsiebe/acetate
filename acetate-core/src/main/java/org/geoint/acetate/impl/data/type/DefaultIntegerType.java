package org.geoint.acetate.impl.data.type;

import java.io.IOException;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.DataType;

/**
 * Default data type for {@link Integer}.
 */
@DefaultType
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
