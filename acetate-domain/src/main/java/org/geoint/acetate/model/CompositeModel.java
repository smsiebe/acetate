package org.geoint.acetate.model;

import java.util.Map;

/**
 * A data model type which is comprised of zero or more objects.
 *
 * @param <T> java object type used to represent this model
 */
public interface CompositeModel<T> extends DataModel<T> {

    /**
     * Returns all the component models contained by this composite.
     *
     * @return all models contained by this composite
     */
    Map<String, CompositeComponentModel<?>> getComponentModels();
}
