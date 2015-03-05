package org.geoint.acetate.bind;

import java.util.Collection;
import org.geoint.acetate.metamodel.DataModel;

/**
 * The binder links the DataModel data values.
 *
 * @param <D> the object type being bound
 */
public interface BoundData<D> {

    /**
     * Retrieve the structured model for this data.
     *
     * @return the data object
     */
    DataModel<D> getModel();

    /**
     * Java Object from the bound data.
     *
     * @return bound data object
     */
    D getValue();

    /**
     * Data bound to the requested field.
     *
     * @param path absolute component path
     * @return field binder for the provided field name
     */
    BoundComponent<?> getComponent(String path);

    /**
     * Collection of data instances that are bound to a component name.
     * @param path absolute component path
     * @return bound component
     */
    Collection<? extends BoundComponent<?>> getComponentCollection(String path);

}
