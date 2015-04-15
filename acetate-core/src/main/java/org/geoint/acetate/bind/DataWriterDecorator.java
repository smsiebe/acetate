package org.geoint.acetate.bind;

import java.nio.ByteBuffer;

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

    public void write(String position, String value) throws DataBindException {
        writer.write(position, value);
    }

    public void write(String position, ByteBuffer buffer) throws DataBindException {
        writer.write(position, buffer);
    }

    public void write(String position, ByteBuffer buffer, int offset, int length) throws DataBindException {
        writer.write(position, buffer, offset, length);
    }

}
