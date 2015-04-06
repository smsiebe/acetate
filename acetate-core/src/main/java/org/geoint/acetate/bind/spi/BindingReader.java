package org.geoint.acetate.bind.spi;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Marker interface for a binding reader.
 *
 * A BindingReader of one of the supported types (string, binary, or object) is
 * returned by the {@link DataBinder}.
 *
 * @see StringDataReader
 * @see BinaryDataReader
 * @see TypedDataReader
 */
public interface BindingReader {

    public enum ComponentType {

        /**
         * A container for multiple data or object types.
         */
        ARRAY,
        /**
         * A "data bag".
         */
        OBJECT,
        /**
         * Data with a value.
         */
        DATA;
    }

    /**
     * Provides a component "hint" as to the next component type that is
     * encountered by the reader.
     *
     * @return component type hint or null if EOF
     * @throws IOException thrown if there is a problem reading
     */
    ComponentType nextType() throws IOException;

    /**
     * Returns the component path of the next component.
     *
     * @return the field path of the next component
     * @throws IOException thrown if there is a problem reading
     * @throws EOFException thrown if there are no more data components to read
     */
    String nextPath() throws IOException;

    /**
     * Skip the next data component.
     *
     * @throws IOException thrown if there is a problem advancing the binder
     * @throws EOFException thrown if there are no more components to read
     */
    void skip() throws IOException, EOFException;

    /**
     * Skips to the next component of the specified path.
     *
     * @param path data component path
     * @throws IOException thrown if there is a problem advancing the binder
     * @throws EOFException thrown if there are no more components to read
     */
    void skipTo(String path) throws IOException, EOFException;

}
