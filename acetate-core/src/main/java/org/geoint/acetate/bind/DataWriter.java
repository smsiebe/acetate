package org.geoint.acetate.bind;

import java.nio.ByteBuffer;
import org.geoint.acetate.model.ClassModel;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.FieldModel;

/**
 *
 */
public interface DataWriter {

    void startArray(ComponentModel model) throws DataBindException;

    void endArray() throws DataBindException;

    void startObject(ClassModel model) throws DataBindException;

    void endObject() throws DataBindException;

    void field(FieldModel model) throws DataBindException;

    void write(ByteBuffer buffer) throws DataBindException;

    void write(ByteBuffer buffer, int offset, int length) throws DataBindException;

    void write(Object obj) throws DataBindException;

}
