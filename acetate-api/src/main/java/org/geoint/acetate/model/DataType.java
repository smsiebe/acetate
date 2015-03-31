package org.geoint.acetate.model;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Object which provides metadata on a data type as well as behavior to convert
 * the data type to/from binary, String, and Object representations.
 *
 * @param <T> java object for this type
 */
public interface DataType<T> {

    /**
     * The Java class this data item is represented as.
     *
     * @return java class type
     */
    Class<T> getType();

    /**
     * Converts the data as a UTF-8 String, reading all the remaining data from
     * the buffer, from its current position.
     *
     * @param buff buffer to read data from
     * @return data as UTF-8 String
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    String asString(ByteBuffer buff)
            throws InvalidDataTypeException;

    /**
     * Converts UTF-8 string representation of data to the byte buffer at its
     * current position.
     *
     * @param utf8 utf-8 data representation
     * @param buffer destination buffer
     * @return number of bytes written to buffer
     * @throws BufferOverflowException thrown if the source string bytes
     * overflow the destination buffer
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    long asBytes(String utf8, ByteBuffer buffer) throws BufferOverflowException,
            InvalidDataTypeException;

    /**
     * Converts a UTF-8 string representation of the data to bytes.
     *
     * @param utf8 utf-8 representation of the data
     * @return data
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    byte[] asBytes(String utf8)
            throws InvalidDataTypeException;

    /**
     * Converts the remaining data in the buffer to a Java Object.
     *
     * @param buffer source data
     * @return java Object
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    T asObject(ByteBuffer buffer)
            throws InvalidDataTypeException;

    /**
     * Converts the UTF-8 string representation of the data to a Java Object.
     *
     * @param utf8 utf8 string representation of data
     * @return java object
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    T asObject(String utf8) throws InvalidDataTypeException;

    /**
     * Converts the Object as bytes, writing to the buffer.
     *
     * @param object source data
     * @param buffer buffer
     * @return number of bytes written to buffer
     * @throws BufferOverflowException thrown if the byte representation of the
     * object overflows the provided buffIer
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    long asBytes(T object, ByteBuffer buffer) throws BufferOverflowException,
            InvalidDataTypeException;

    /**
     * Converts the Object to bytes.
     *
     * @param object java Object representation of data
     * @return data
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    byte[] asBytes(T object)
            throws InvalidDataTypeException;

}
