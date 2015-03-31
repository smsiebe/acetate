package org.geoint.acetate.model;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * DataType that supports a String format parameter.
 *
 * @param <T> java object type
 */
public interface FormattableDataType<T> extends DataType<T> {

    /**
     * Converts the data as a UTF-8 String, reading all the remaining data from
     * the buffer, from its current position.
     *
     * @param buff buffer to read data from
     * @param format format pattern as defined by the data type
     * @return data as UTF-8 String
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    String asString(ByteBuffer buff, String format) throws InvalidDataTypeException;

    /**
     * Converts UTF-8 string representation of data to the byte buffer at its
     * current position.
     *
     * @param str utf-8 data representation
     * @param format format pattern as defined by the data type
     * @param buffer destination buffer
     * @return number of bytes written to buffer
     * @throws BufferOverflowException thrown if the source string bytes
     * overflow the destination buffer
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    long asBytes(String str, String format, ByteBuffer buffer)
            throws BufferOverflowException, InvalidDataTypeException;

    /**
     * Converts a UTF-8 string representation of the data to bytes.
     *
     * @param str utf-8 representation of the data
     * @param format format pattern as defined by the data type
     * @return data
     * @throws InvalidDataTypeException if the DataType cannot convert the
     * provided data
     */
    byte[] asBytes(String str, String format) throws InvalidDataTypeException;

    /**
     * Converts the data to an object, reading all the remaining data from the
     * buffer, from its current position.
     *
     * @param buff data source
     * @param format format pattern as defined by the data type
     * @return
     * @throws InvalidDataTypeException
     */
    T asObject(ByteBuffer buff, String format) throws InvalidDataTypeException;

    /**
     * Converts a UTF-8 string representation of the data to object
     *
     * @param str utf-8 representation of the data
     * @param format format pattern as defined by the data type
     * @return
     * @throws InvalidDataTypeException
     */
    T asObject(String str, String format) throws InvalidDataTypeException;
}
