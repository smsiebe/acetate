package org.geoint.acetate.data;

import gov.ic.geoint.acetate.bind.DataBindException;
import java.io.IOException;
import java.util.Optional;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.io.ByteReader;

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
    ObjectModel<T> getModel();

    /**
     * Attempt to convert the node value to a java object.
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
     * @return
     * @throws DataConversionException
     */
    String asString() throws DataConversionException;

    
    /**
     * Returns the data value as bytes.
     *
     * @return value as bytes or null
     */
    byte[] asBytes();

    /**
     * Returns a byte reader of the data content.
     *
     * @return
     */
    ByteReader getBytes();

    /**
     * Write the data value as bytes to the provided writer.
     *
     * @param writer used to write the bytes
     * @throws IOException thrown if there were problems writing the bytes to
     * the provided writer
     */
    void asBytes(ByteWriter writer) throws IOException;
}
