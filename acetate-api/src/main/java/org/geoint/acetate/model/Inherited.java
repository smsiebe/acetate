package org.geoint.acetate.model;

import java.util.Collection;

/**
 * A domain model component which inherits from another.
 *
 * @param <T>
 */
public interface Inherited<T> {

    /**
     * Component models from which this component inherits/specializes.
     *
     * @return inherited (parent) components
     */
    Collection<T> inheritsFrom();

}
