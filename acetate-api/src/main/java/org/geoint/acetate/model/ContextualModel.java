package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;
import org.geoint.acetate.model.constraint.Constrained;

/**
 * Contextual wrapper for domain model components that are used in the context
 * of another domain model component.
 *
 */
@Domain(name = "acetate", version = 1)
public interface ContextualModel extends Constrained{

    /**
     * Model of the domain model component that this component is within
     * context.
     *
     * @return context model
     */
    ModelComponent getContextModel();

    /**
     * The context relationship type.
     *
     * @return context relationship type
     */
    ContextType getContextType();
}
