package org.geoint.acetate.bind;

import org.geoint.acetate.structure.StructureType;
import java.util.Optional;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.transform.StringConverter;
import org.geoint.acetate.transform.DataTransformException;

/**
 * A reader which knows about its DataModel a priori and uses this information
 * during data reading to differentiate between structured (data defined by the
 * DataModel) and unstructured (not defined in the DataModel) data.
 *
 * @param <T> model type
 */
public interface StructuredDataReader<T> extends DataReader {

    /**
     * Advances the reader to the next data component, optionally skipping or
     * including the component if it is not defined in the model.
     *
     * @param includeUnstructured true to include unstructured data content
     * @return data model component type for the current reader position
     * @throws DataBindException if there were any problems reading or
     * determining the new structure component
     */
    StructureType next(boolean includeUnstructured)
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
    Optional<DataModel<T>> getModel();

    /**
     * Returns the value of the current data component as an object.
     *
     * @return the object or null if the component does not have a value
     * @throws DataBindException thrown if the value content could not be read
     * @throws DataTransformException thrown if the value could not be converted
     * or inverted to/from the desired type or format
     */
    Optional<Object> value() throws DataBindException, DataTransformException;

    /**
     * Converts the value of the current data component to the default data
     * format.
     *
     * @return value as String or null if the component does not have a value
     * @throws DataBindException thrown if the value content could not be read
     * @throws DataTransformException thrown if the value could not be converted
     * or inverted to/from the desired type or format
     */
    Optional<String> valueAsString()
            throws DataBindException, DataTransformException;

    /**
     * Converts the value of the current data component with the provided
     * formatter.
     *
     * @param formatter formatter used to convert the data
     * @return value as formatted String or null if the component does not have
     * a value
     * @throws DataBindException thrown if the value content could not be read
     * @throws DataTransformException thrown if the value could not be converted
     * or inverted to/from the desired type or format
     */
    Optional<String> valueAsString(StringConverter formatter)
            throws DataBindException, DataTransformException;
}
