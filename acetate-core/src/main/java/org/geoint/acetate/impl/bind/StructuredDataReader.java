package org.geoint.acetate.impl.bind;

import java.io.IOException;
import java.util.Optional;
import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.structure.DataStructure;
import org.geoint.acetate.codec.DataConversionException;

/**
 * A reader which knows about its data structure a priori and uses this
 * information during data reading to differentiate between structured (data
 * defined by the data structure) and unstructured (not defined in the data
 * structure) data.
 *
 */
public interface StructuredDataReader extends DataReader {

    /**
     * Advances the reader to the next data component, optionally skipping or
     * including the component if it is not defined in the model.
     *
     * @param includeUnstructured true to include unstructured data content
     * @return data model component type for the current reader position
     * @throws DataBindException if there were any problems reading or
     * determining the new structure component
     */
    DataStructureType next(boolean includeUnstructured)
            throws DataBindException;

    /**
     * Indicates if the position of this reader is currently pointing to an
     * unstructured component.
     *
     * @return true if the current reader position points to an unstructured
     * component, otherwise false
     * @throws DataBindException
     */
    boolean isUnstructured() throws DataBindException;

    /**
     * Data meta model of the current data component.
     *
     * @return meta model of the current component, if it has a meta model
     */
    Optional<DataStructure> getStructure();

    /**
     * Returns the value of the current data component as an object.
     *
     * @return the object or null if the component does not have a value
     * @throws DataBindException thrown if the value content could not be read
     * @throws DataConversionException thrown if the value could not be
     * converted or inverted to/from the desired type or format
     */
    Optional<Object> value() throws DataBindException, DataConversionException;

}
