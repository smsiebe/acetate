package org.geoint.acetate.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Writer of character data
 */
public interface CharacterWriter extends Flushable, Closeable, AutoCloseable {

    /**
     * Writes the content of the byte array.
     *
     * @param data data as String
     * @throws IOException thrown if there are problems writing the data
     */
    void write(String data) throws IOException;

    /**
     * Writes the content of the buffer.
     *
     * @param buffer data
     * @throws IOException thrown if there are problems writing the data
     */
    void write(StringBuffer buffer) throws IOException;

}
