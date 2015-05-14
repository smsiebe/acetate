package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentPath;

/**
 * Context path for component operations.
 */
public interface OperationPath extends ComponentPath {

    /**
     * Create a context path for a parameter of this operation.
     *
     * @param paramName
     * @return operation parameter context path
     */
    public ImmutableComponentPath parameter(String paramName);

    /**
     * Create a context path for the return component of this operation.
     *
     * @return operation return context path
     */
    public ImmutableComponentPath returned();
}
