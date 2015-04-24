package org.geoint.acetate.bind;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.structure.DataStructure;
import org.geoint.acetate.transform.DataConversionException;

/**
 * Decorates a {@link StructuredDataReader}.
 *
 */
public abstract class StructuredDataReaderDecorator
        implements StructuredDataReader {

    protected StructuredDataReader reader;

    @Override
    public DataStructureType next(boolean includeUnstructured) throws DataBindException {
        return reader.next(includeUnstructured);
    }

    @Override
    public boolean isUnstructured() throws DataBindException {
        return reader.isUnstructured();
    }

    @Override
    public Optional<DataStructure> getStructure() {
        return reader.getStructure();
    }

    @Override
    public Optional<Object> value() throws DataBindException, DataConversionException {
        return reader.value();
    }

    @Override
    public DataStructureType next() throws DataBindException {
        return reader.next();
    }

    @Override
    public Optional<String> position() throws DataBindException {
        return reader.position();
    }

    @Override
    public Optional<ByteReader> read() {
        return reader.read();
    }

    @Override
    public void writeBytes(ByteWriter writer) throws IOException {
        reader.writeBytes(writer);
    }

}
