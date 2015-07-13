package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Set;

/**
 *
 *
 */
public interface ObjectModel<T> extends CompositeModel<T>{



    /**
     * Behavior declared directly by this object model.
     *
     * @return object operation models declared by this model
     */
    Collection<BehaviorModel> getDeclaredBehavior();

    /**
     * All object behavior, including those inherited from parent object
     * models.
     *
     * @return all object operation models
     */
    Collection<BehaviorModel> getBehavior();

    /**
     * Domain models from which this model extends.
     *
     * @return parent object models
     */
    Set<DomainModel> getParents();

    /**
     * Object model classes that extends this model.
     *
     * @return specialized types of this model
     */
    Collection<ObjectModel<? extends T>> getSpecialized();

}
