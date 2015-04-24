package org.geoint.acetate.bind;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;

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
