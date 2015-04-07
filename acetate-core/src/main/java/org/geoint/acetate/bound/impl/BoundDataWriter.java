package org.geoint.acetate.bound.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.geoint.acetate.bind.spi.BinaryDataWriter;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.acetate.bind.spi.StringDataWriter;
import org.geoint.acetate.bind.spi.TypedDataWriter;
import org.geoint.acetate.bound.BoundData;

/**
 * DataWriter which creates a {@link BoundData} instance.
 *
 * This writer supports any data format.
 */
public class BoundDataWriter implements BindingWriter, StringDataWriter,
        BinaryDataWriter, TypedDataWriter {

    @Override
    public void array() throws IOException {

    }

    @Override
    public void write(String path, String data) throws IOException {

    }

    @Override
    public void write(String path, byte[] data) throws IOException {

    }

    @Override
    public void write(String path, ByteBuffer buffer) throws IOException {

    }

    @Override
    public void write(String path, ByteBuffer buffer, int offset, int length) throws IOException {

    }

    @Override
    public void write(BoundData data) throws IOException {

    }

    public BoundData getBoundData() {

    }

}
