package org.geoint.acetate.spi.model;

import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelException;

/**
 * Provider of a {@link DataModel}.
 */
@FunctionalInterface
public interface ModelProvider {

    /**
     * Determine the {@link DataModel} of a Class.
     *
     * @param <T> class type
     * @return data model
     * @throws ModelException thrown if there are problems modeling this class
     */
    public <T> DataModel<T> model() throws ModelException;
}
