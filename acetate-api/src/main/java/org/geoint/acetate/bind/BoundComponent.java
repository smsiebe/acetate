package org.geoint.acetate.bind;

import java.util.Collection;
import org.geoint.acetate.metamodel.DataModel;

/**
 * A component within the object graph from which data values can be retreived.
 */
public interface BoundComponent<T> {

    /**
     * Return the object value of the response.
     *
     * @return component value
     */
    T getValue();

    /**
     * Data model used to bind the data.
     *
     * @return data model used in binding
     */
    DataModel<T> getModel();

    /**
     * Retrieves an object component by absolute path name.
     *
     * @param path absolute component path name
     * @return bound component or null if no component exists at that path
     */
    BoundComponent<?> getComponent(String path);

    /**
     * Retrieves an object as a collection.
     *
     * @param path absolute component path
     * @return collection of components at the specified path
     */
    Collection<BoundComponent<?>> getComponentCollection(String path);
}
