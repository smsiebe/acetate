package org.geoint.acetate.model;

import java.util.Collection;

/**
 * A composite component which has specialized (inherited from) one or more
 * model components.
 *
 * @param <T> data type of this component
 */
public interface SpecializedCompositeComponentModel<T>
        extends CompositeComponentModel<T> {

    /**
     * Component models from which this component inherits.
     *
     * @return one or more components from which the model inherits
     */
    Collection<ComponentModel<?>> getBaseModels();

}
