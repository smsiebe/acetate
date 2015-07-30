package org.geoint.acetate.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Converts an object to/from bytes or string format.
 *
 * @param <T> domain datatype supported
 */
public interface DataCodec<T> {

    void asBytes(T data, OutputStream out) throws IOException;

    void asString(T data, Appendable appendable) throws IOException;

    T fromBytes(InputStream in) throws IOException;

    T fromString(Readable reable) throws IOException;
}
