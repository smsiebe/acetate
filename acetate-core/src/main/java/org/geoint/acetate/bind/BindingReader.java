package org.geoint.acetate.bind;

import java.io.EOFException;
import java.io.IOException;

/**
 * 
 *
 */
public interface BindingReader<T> {

    public enum ComponentType {

        /**
         * Start a data container for multiple component instances.
         */
        START_ARRAY,
        /**
         * Close the data container.
         */
        END_ARRAY,
        /**
         * Start a "data bag".
         */
        START_OBJECT,
        /**
         * End a "data bag".
         */
        END_OBJECT,
        /**
         * Data with a value.
         */
        FIELD;
    }

    /**
     * Advances the reader to the next component type, providing a component
     * "hint" as to the next component type that is encountered by the reader.
     *
     * @return component type hint or null if EOF
     * @throws IOException thrown if there is a problem reading
     */
    ComponentType next() throws IOException;

    /**
     * Skips to the next component of the specified path.
     *
     * @param path data component path
     * @return component type hint or null if EOF
     * @throws IOException thrown if there is a problem advancing the binder
     */
    ComponentType skipTo(String path) throws IOException;

    /**
     * Returns the component path of the current component.
     *
     * @return the field path of the next component
     * @throws IOException thrown if there is a problem reading
     * @throws EOFException thrown if there are no more data components to read
     */
    String path() throws IOException;

}
