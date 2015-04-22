package org.geoint.acetate.bind;

import org.geoint.acetate.structure.StructureType;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * Decorates a {@link DataReader}.
 *
 */
public abstract class DataReaderDecorator implements DataReader {

    protected final DataReader reader;

    public DataReaderDecorator(DataReader reader) {
        this.reader = reader;
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
