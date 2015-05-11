package org.geoint.acetate.impl.bind;

import java.nio.ByteBuffer;
import org.geoint.acetate.io.ByteReader;

/**
 * Decorates a DataWriter.
 */
public abstract class DataWriterDecorator {

    protected final DataWriter writer;

    public DataWriterDecorator(DataWriter writer) {
        this.writer = writer;
    }

    public void startObject(String position) throws DataBindException {
        writer.startObject(position);
    }

    public void endObject() throws DataBindException {
        writer.endObject();
    }

    public void startArray(String position) throws DataBindException {
        writer.startArray(position);
    }

    public void endArray() throws DataBindException {
        writer.endArray();
    }

    public void write(String position, byte[] value) throws DataBindException {
        writer.write(position, value);
    }

    public void write(String position, ByteReader reader) throws DataBindException {
        writer.write(position, reader);
    }

}
