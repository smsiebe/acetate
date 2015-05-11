package org.geoint.acetate.codec;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

/**
 * Provide a constructor which allows the convert and invert methods to be
 * provided as a method reference or defined functionally.
 */
public final class FunctionalBinaryCodec implements BinaryCodec {

    private final BinaryConverter converter;
    private final BinaryConverter inverter;

    public FunctionalBinaryCodec(BinaryConverter converter,
            BinaryConverter inverter) {
        this.converter = converter;
        this.inverter = inverter;
    }

    @Override
    public void convert(ByteReader reader, ByteWriter writer)
            throws DataConversionException {
        converter.convert(reader, writer);
    }

    @Override
    public void invert(ByteReader reader, ByteWriter writer)
            throws DataConversionException {
        inverter.convert(reader, writer);
    }

}
