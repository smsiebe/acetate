package org.geoint.acetate.impl.codec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.io.ByteWriter;
import gov.ic.geoint.acetate.bind.DataType;

/**
 * Default data type for {@link String}.
 */
@DefaultType
public final class DefaultStringType implements DataType<String> {

    @Override
    public Class<String> getTypeClass() {
        return String.class;
    }

    @Override
    public void toBytes(String instance, ByteWriter writer)
            throws IOException {
        writer.write(instance.getBytes(StandardCharsets.UTF_8));
    }

}
