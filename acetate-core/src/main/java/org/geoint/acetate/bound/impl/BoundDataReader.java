package org.geoint.acetate.bound.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.geoint.acetate.bind.spi.BinaryDataReader;
import org.geoint.acetate.bind.spi.BindingReader;
import org.geoint.acetate.bind.spi.StringDataReader;
import org.geoint.acetate.bind.spi.TypedDataReader;
import org.geoint.acetate.bound.BoundData;

/**
 * Reads {@link BoundData} as a source.
 */
public class BoundDataReader implements BindingReader, StringDataReader,
        BinaryDataReader, TypedDataReader {

    private final BoundData data;

    public BoundDataReader(BoundData data) {
        this.data = data;
    }

    @Override
    public ComponentType next() throws IOException {

    }

    @Override
    public ComponentType skipTo(String path) throws IOException {

    }

    @Override
    public String path() throws IOException {

    }

    @Override
    public String read() throws IOException, EOFException {

    }

    @Override
    public void read(Appendable appendable) throws IOException, EOFException {

    }

    @Override
    public void read(OutputStream out) throws IOException, EOFException {

    }

    @Override
    public void read(ByteBuffer buffer) throws IOException, EOFException {

    }

}
