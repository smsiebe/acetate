package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Set;

/**
 * A component of the domain model which is composed from other domain model
 * components (and which in turn may be used to compose further composite
 * components).
 *
 * @param <T> associated java type for this composite component
 */
public interface CompositeComponentModel<T> extends ComponentModel<T> {

    /**
     * Names of all then components contained by this model.
     *
     * @return composite component names
     */
    Set<String> getCompositeNames();

    /**
     * Components contained by this model.
     *
     * @return composite components
     */
    Collection<CompositeComponent> getComposites();

}
