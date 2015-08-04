package org.geoint.acetate.model.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.data.Codec;
import org.geoint.acetate.data.DataCodec;
import org.geoint.acetate.data.StreamUtils;
import org.geoint.acetate.domain.annotation.Domain;
import org.geoint.acetate.domain.DomainConverter;
import org.geoint.acetate.model.common.StandardInteger.StandardIntegerCodec;

/**
 *
 */
@Domain(domain = "org.geoint.acetate.std")
@Codec(StandardIntegerCodec.class)
public class StandardInteger {

    private final int integer;

    private StandardInteger(int integer) {
        this.integer = integer;
    }

    public static class StandardIntegerCodec implements DataCodec<StandardInteger> {

        @Override
        public void asBytes(StandardInteger data, OutputStream out)
                throws IOException {
            out.write(Integer.valueOf(data.integer).byteValue());
        }

        @Override
        public StandardInteger fromBytes(InputStream in)
                throws IOException {
            return new StandardInteger(in.read());
        }

        @Override
        public void asString(StandardInteger data, Appendable appendable)
                throws IOException {
            appendable.append(String.valueOf(data.integer));
        }

        @Override
        public StandardInteger fromString(Readable readable) throws IOException {
            return new StandardInteger(Integer.parseInt(StreamUtils.readString(readable)));
        }

    }

    public static class StandardIntegerConverter implements
            DomainConverter<Integer, StandardInteger> {

        @Override
        public StandardInteger convert(Integer obj) {
            return new StandardInteger(obj);
        }

        @Override
        public Integer invert(StandardInteger domainObj) {
            return domainObj.integer;
        }

    }
}
