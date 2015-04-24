package org.geoint.acetate.bind;

import org.geoint.acetate.io.ByteReader;

/**
 * Low-level data structure/format specific writer.
 */
public interface DataWriter {

    void startObject(String position) throws DataBindException;

    void endObject() throws DataBindException;

    void startArray(String position) throws DataBindException;

    void endArray() throws DataBindException;

    void write(String position, byte[] value) throws DataBindException;

    void write(String position, ByteReader reader) throws DataBindException;

}
