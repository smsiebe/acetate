
package org.geoint.acetate.impl.data.type;

import java.io.IOException;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.DataType;

/**
 * Default date type for {@link Boolean}.
 * 
 */
public final class DefaultBooleanType implements DataType<Boolean>{

    @Override
    public Class<Boolean> getTypeClass() {
        return Boolean.class;
    }

    @Override
    public void toBytes(Boolean instance, ByteWriter writer) throws IOException {
        writer.write((instance) ? (byte) 1 : (byte) 0);
    }

}
