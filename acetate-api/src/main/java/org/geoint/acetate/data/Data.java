package org.geoint.acetate.data;

import gov.ic.geoint.acetate.bind.DataBindException;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.data.transform.DataConversionException;
import org.geoint.acetate.model.ObjectModel;

/**
 * Root of a generic data graph.
 *
 * @param <T> data type of the data instance
 */
public interface Data<T> {

    /**
     * The model of this data instance.
     *
     * @return
     */
    ObjectModel<T> getModel();

    /**
     * Return the root data node.
     *
     * @return root data node
     */
    DataNode<T> get();

    /**
     * Converts the data to a java object.
     *
     * @return data as object
     * @throws DataConversionException
     * @throws DataBindException
     */
    Optional<T> asObject()
            throws DataConversionException, DataBindException;

    /**
     * Returns all data nodes at the specified position.
     *
     * @param position dot-separated structural position
     * @return nodes at the specified position
     */
    Collection<DataNode<?>> byPosition(String position);

}
