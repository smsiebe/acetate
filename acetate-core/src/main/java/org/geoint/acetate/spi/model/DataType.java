package org.geoint.acetate.spi.model;

import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.model.ValueModel;
import org.geoint.acetate.transform.DataFormatException;

/**
 * A DataType is acetates equivalent to a "primitive".
 *
 * A DataType must {@link Class#equals(Class) match} the type specified by
 * DataType. Subclasses will not match.
 *
 * When the data type of a model component has a corresponding DataType, the
 * data model will treat that component as a {@link ValueModel}, no matter how
 * complex that class might actually be, and will not attempt to model any
 * deeper for that class.
 *
 * @param <T> java type for data
 */
public interface DataType<T> {

    /**
     * The corresponding Java class type.
     *
     * @return java type this DataType represents
     */
    Class<T> getType();

    /**
     * Validate the data format/value is appropriate for this data type.
     *
     * @param bb buffer
     * @return true if the buffered data is valid for this data type
     * @throws NullPointerException thrown if the buffer was null or did not
     * contain any data
     */
    boolean validate(ByteBuffer bb)
            throws NullPointerException;

    /**
     * Validate the data format/value is appropriate for this data type.
     *
     * @param bb buffer
     * @param offset start position
     * @param len number of bytes to read from buffer
     * @return true if the buffered data is valid for this data type
     * @throws NullPointerException thrown if the buffer was null or did not
     * contain any data
     */
    boolean validate(ByteBuffer bb, int offset, int len)
            throws NullPointerException;

    /**
     * Validate the format/value is valid for the object representation of the
     * data type.
     *
     * @param obj data as object
     * @return true if the object can be handled by this DataType
     * @throws NullPointerException thrown if the object was null
     */
    boolean validate(T obj) throws NullPointerException;

    /**
     * Converts the content of the buffer to a Java object instance.
     *
     * Each DataType should clearly define the (binary) data format of the data
     * it is expecting; failing to pass the data in this format will result in
     * the DataFormatException. Data read in from a source not conforming to
     * this format must first be converted to this format. This is done by
     * defining a (or a chain of) {@link BinaryCodec binary codecs} in the
     * {@link DataStructure}.
     *
     * @param bb buffer
     * @return corresponding java instance for this data type or null if the
     * buffer was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     * @throws NullPointerException thrown if the buffer was null or did not
     * contain any data
     */
    Optional<T> valueOf(ByteBuffer bb) throws DataFormatException,
            NullPointerException;

    /**
     * Similar to {@link #valueOf(ByteBuffer)} but allows the buffer content
     * location to be specified.
     *
     * @param bb buffer
     * @param offset start position
     * @param length number of bytes to read from buffer
     * @return corresponding java instance for this data type or null if the
     * buffer was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     * @throws NullPointerException thrown if the buffer was null or did not
     * contain any data
     */
    Optional<T> valueOf(ByteBuffer bb, int offset, int length)
            throws DataFormatException, NullPointerException;
}
