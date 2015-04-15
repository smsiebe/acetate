package org.geoint.acetate.bind;

import java.nio.ByteBuffer;

/**
 * Low-level data structure/format specific writer.
 */
public interface DataWriter {

    void startObject(String position) throws DataBindException;

    void endObject() throws DataBindException;

    void startArray(String position) throws DataBindException;

    void endArray() throws DataBindException;

    void write(String position, String value) throws DataBindException;

    void write(String position, ByteBuffer buffer) throws DataBindException;

    void write(String position, ByteBuffer buffer, int offset, int length)
            throws DataBindException;

}
