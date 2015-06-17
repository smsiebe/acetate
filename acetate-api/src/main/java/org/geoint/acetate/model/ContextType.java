package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Model;

/**
 * Contextual domain model relationship types.
 *
 */
@Model(name="", domainName="acetate", domainVersion=1)
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
