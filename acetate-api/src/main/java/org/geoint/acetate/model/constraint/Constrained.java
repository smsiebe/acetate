package org.geoint.acetate.model.constraint;

import java.util.Collection;

/**
 * A model component is capable of being constrained by one or more
 * {@link ComponentConstraint}.
 */
public interface Constrained {

    /**
     * Data constraints applied to this model component.
     *
     * @return immutable collection of constraints for this model component
     */
    Collection<ComponentConstraint> getConstraints();
}
