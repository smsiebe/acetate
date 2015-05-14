package org.geoint.acetate.data;

import java.util.Collection;
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
     * Returns all data nodes at the specified position.
     *
     * @param position dot-separated structural position
     * @return nodes at the specified position
     */
    Collection<DataNode<?>> byPosition(String position);

}
