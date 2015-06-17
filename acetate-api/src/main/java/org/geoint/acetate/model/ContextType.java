package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Contextual domain model relationship types.
 *
 */
@Domain(name = "acetate", version = 1)
public enum ContextType {

    /**
     * The domain model component is used to compose (define) the contextual
     * component.
     */
    COMPOSITE,
    /**
     * The component is a parameter of an operation.
     */
    OPERATION_PARAMETER,
    /**
     * The component is the return type of an operation.
     */
    OPERATION_RETURN;

}
