package org.geoint.acetate.entity.attic.constraint;

import java.util.Collection;

/**
 * A model component is capable of being constrained by
 * {@link DataConstraint constraints}.
 */
public interface Constrained {

    /**
     * Data constraints applied to this model component.
     *
     * @return immutable collection of constraints for this model component
     */
    Collection<DataConstraint> getConstraints();
}
