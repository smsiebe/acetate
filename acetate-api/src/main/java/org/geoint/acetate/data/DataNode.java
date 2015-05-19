package org.geoint.acetate.data;

import gov.ic.geoint.acetate.bind.DataBindException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Optional;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.data.transform.DataConversionException;

/**
 * A basic node of the data graph.
 *
 * @param <T> data type
 * @see CompositeDataNode
 * @see ValueDataNode
 */
public interface DataNode<T> {

    /**
     * Structural position of a data node.
     *
     * Note that multiple data nodes may have the same position (such as the
     * case of an array or map).
     *
     * @return structural position of node
     */
    DataPath getDataPath();

    /**
     * The model of this node.
     *
     * @return component model for this data node
     */
    DomainObject<T> getModel();

    /**
     * Converts the node value to a java object.
     *
     * @return java object or null if the value was null
     * @throws DataConversionException thrown if the binary data could not be
     * converted to the required format for the data type
     * @throws DataBindException thrown if the data could not be instantiated
     */
    Optional<T> asObject()
            throws DataConversionException, DataBindException;

    /**
     * Returns the data as a String.
     *
     * @return value as String or null if the data was null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to a String
     */
    String asString() throws DataConversionException;

    /**
     * Returns the content of the node as a String or the default value if the
     * node content was null.
     *
     * @param defaultValue default node value
     * @return node value or default value if content of node was null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to a String
     */
    String asString(String defaultValue) throws DataConversionException;

    /**
     * Write the data value as a character stream to the provided buffer.
     *
     * @param buffer to write the data
     * @throws DataConversionException thrown if there was as problem converting
     * the data to characters
     * @throws IOException thrown if there were problems writing the characters
     * to the provided writer
     */
    void asString(CharBuffer buffer) throws DataConversionException, IOException;

    /**
     * Writes the data of this node as characters to the provided writer, or
     * writes the default data if this nodes data was null.
     *
     * @param buffer to write the data
     * @param defaultData default data if the data node value is null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to characters
     * @throws IOException thrown if there were problems writing to the provided
     * writer
     */
    void asString(CharBuffer buffer, String defaultData)
            throws DataConversionException, IOException;

    /**
     * Returns the data value as bytes.
     *
     * @return value as bytes or null if the data was null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to bytes
     */
    byte[] asBytes() throws DataConversionException;

    /**
     * Returns the data value as bytes or the default data value if the data
     * node did not contain data.
     *
     * @param defaultValue default data value
     * @return value of data node or default data value if node was null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to bytes
     */
    byte[] asBytes(byte[] defaultValue) throws DataConversionException;

    /**
     * Write the data value as bytes to the provided writer.
     *
     * @param writer buffer to write the data
     * @throws DataConversionException thrown if there was as problem converting
     * the data to bytes
     * @throws IOException thrown if there were problems writing the bytes to
     * the provided writer
     */
    void asBytes(ByteBuffer writer) throws DataConversionException, IOException;

    /**
     * Writes the data of this node as bytes to the provided writer, or writes
     * the default data if this nodes data was null.
     *
     * @param writer buffer to write the data
     * @param defaultData default data if the data node value is null
     * @throws DataConversionException thrown if there was as problem converting
     * the data to bytes
     * @throws IOException thrown if there were problems writing to the provided
     * writer
     */
    void asBytes(ByteBuffer writer, byte[] defaultData)
            throws DataConversionException, IOException;
}
