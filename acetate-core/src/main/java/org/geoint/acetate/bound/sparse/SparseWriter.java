package org.geoint.acetate.bound.sparse;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 */
@FunctionalInterface
public interface SparseWriter {

    /**
     * Writes the remaining buffer.
     *
     * @param buffer
     * @throws IOException
     */
    default void write(ByteBuffer buffer) throws IOException {
        this.write(buffer, buffer.position(), buffer.remaining());
    }

    void write(ByteBuffer buffer, int offset, int length) throws IOException;

}
