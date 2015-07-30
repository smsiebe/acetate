package org.geoint.acetate.model.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.CharBuffer;
import org.geoint.acetate.data.Buffers;
import org.geoint.acetate.data.DataCodec;
import org.geoint.acetate.domain.annotation.Domain;
import org.geoint.acetate.model.DomainConverter;

/**
 *
 */
@Domain(domain = "org.geoint.acetate.std")
public enum StandardBoolean {

    TRUE((byte) 1, true),
    FALSE((byte) 0, false);

    private final byte byteFormat;
    private final boolean bool;

    private StandardBoolean(byte byteFormat, boolean bool) {
        this.byteFormat = byteFormat;
        this.bool = bool;
    }

    public boolean toBoolean() {
        return bool;
    }

    public static class StandardBooleanCodec implements DataCodec<StandardBoolean> {

        @Override
        public void asBytes(StandardBoolean data, OutputStream out) throws IOException {
            out.write(data.byteFormat);
        }

        @Override
        public StandardBoolean fromBytes(InputStream in) throws IOException {
            return (in.read() == TRUE.byteFormat)
                    ? StandardBoolean.TRUE
                    : StandardBoolean.FALSE;
        }

        @Override
        public void asString(StandardBoolean data, Appendable appendable) throws IOException {
            appendable.append(Boolean.toString(data.bool));
        }

        @Override
        public StandardBoolean fromString(Readable readable) throws IOException {
            CharBuffer buffer = CharBuffer.allocate(1);
            Buffers.readFully(buffer, readable);
            buffer.flip();
            return (buffer.get() == 't') ? StandardBoolean.TRUE : StandardBoolean.FALSE;
        }
    }

    public static class StandardBooleanConverter implements
            DomainConverter<Boolean, StandardBoolean> {

        @Override
        public StandardBoolean convert(Boolean obj
        ) {
            return (obj) ? StandardBoolean.TRUE : StandardBoolean.FALSE;
        }

        @Override
        public Boolean invert(StandardBoolean domainObj
        ) {
            return (domainObj.equals(TRUE)) ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}
