package org.geoint.acetate.bind;

import org.geoint.acetate.structure.StructureType;
import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.transform.StringConverter;
import org.geoint.acetate.transform.DataTransformException;

/**
 * Decorates a {@link StructuredDataReader}.
 *
 * @param <T> data type
 */
public abstract class StructuredDataReaderDecorator<T>
        implements StructuredDataReader<T> {

    protected StructuredDataReader<T> reader;

    @Override
    public StructureType next(boolean includeUnstructured) throws DataBindException {
        return reader.next(includeUnstructured);
    }

    @Override
    public boolean isUnstructured() throws DataBindException {
        return reader.isUnstructured();
    }

    @Override
    public Optional<DataModel<T>> getModel() {
        return reader.getModel();
    }

    @Override
    public Optional<Object> value() throws DataBindException, DataTransformException {
        return reader.value();
    }

    @Override
    public Optional<String> valueAsString() throws DataBindException, DataTransformException {
        return reader.valueAsString();
    }

    @Override
    public Optional<String> valueAsString(StringConverter formatter) throws DataBindException, DataTransformException {
        return reader.valueAsString(formatter);
    }

    @Override
    public StructureType next() throws DataBindException {
        return reader.next();
    }

    @Override
    public Optional<String> position() throws DataBindException {
        return reader.position();
    }

    @Override
    public int length() throws DataBindException {
        return reader.length();
    }

    @Override
    public int read(ByteBuffer buffer) throws DataBindException {
        return reader.read(buffer);
    }

    @Override
    public int remaining() throws DataBindException {
        return reader.remaining();
    }

    @Override
    public ByteBuffer read() throws DataBindException {
        return reader.read();
    }

}
