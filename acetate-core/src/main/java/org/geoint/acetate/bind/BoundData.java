package org.geoint.acetate.bind;

import java.util.Collection;
import org.geoint.acetate.metamodel.DataModel;

/**
 * Bound data wraps a DataModel and a DataBinder, providing a programmatic
 * interface to read data from the model.
 *
 */
public interface BoundData extends Hierarchy<BoundComponent> {

    /**
     * Retrieve the structured model for this data.
     *
     * @return the data object
     */
    DataModel getModel();

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
