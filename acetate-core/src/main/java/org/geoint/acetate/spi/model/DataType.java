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
     */
    boolean validate(ByteBuffer bb);

    /**
     * Validate the data format/value is appropriate for this data type.
     *
     * @param bb buffer
     * @param offset start position
     * @param len number of bytes to read from buffer
     * @return true if the buffered data is valid for this data type
     */
    boolean validate(ByteBuffer bb, int offset, int len);

    /**
     * Validate the data format/value is valid for the default String formatting
     * of this data type.
     *
     * @param str data as string
     * @return true if the String is properly formatted
     */
    boolean validate(String str);

    /**
     * Validate the format/value is valid for the object representation of the
     * data type.
     *
     * @param obj data as object
     * @return true if the object is correct for this data type
     */
    boolean validate(T obj);

    /**
     * Converts the content of the buffer to default String format of this value
     * type.
     *
     * @param bb buffer
     * @return default string format of this value type or null if the buffer
     * was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<String> asString(ByteBuffer bb) throws DataFormatException;

    /**
     * Converts the specified content of the buffer to the default String format
     * of this value type.
     *
     * @param bb buffer
     * @param offset start position
     * @param length number of bytes to read from buffer
     * @return default string format of this value type or null if the buffer
     * was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<String> asString(ByteBuffer bb, int offset, int length)
            throws DataFormatException;

    /**
     * Converts the content of the buffer to a Java object instance.
     *
     * @param bb buffer
     * @return corresponding java instance for this data type or null if the
     * buffer was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<T> asObject(ByteBuffer bb) throws DataFormatException;

    /**
     * Converts the content of the buffer to a Java object instance.
     *
     * @param bb buffer
     * @param offset start position
     * @param length number of bytes to read from buffer
     * @return corresponding java instance for this data type or null if the
     * buffer was empty
     * @throws DataFormatException thrown if the buffer was not empty but the
     * content could not be formatted
     */
    Optional<T> asObject(ByteBuffer bb, int offset, int length)
            throws DataFormatException;
}
