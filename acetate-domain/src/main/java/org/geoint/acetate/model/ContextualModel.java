package org.geoint.acetate.model;

import org.geoint.acetate.meta.annotation.Model;

/**
 * Contextual wrapper for domain model components that are used in the context
 * of another domain model component.
 *
 */
@Model(name = "contextualModel", domainName = "acetate", domainVersion = 1)
public interface ContextualModel extends ModelComponent {

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

    /**
     * Contextually-bound domain model for this component.
     *
     * @return contextually bound model
     */
    ContextualDataModel getDataModel();
}
