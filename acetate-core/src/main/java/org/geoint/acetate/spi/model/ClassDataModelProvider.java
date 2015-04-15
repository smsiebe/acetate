package org.geoint.acetate.spi.model;

import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelException;

/**
 * Analyzes a {@link Class} and produces a {@link DataModel}.
 */
public interface ClassDataModelProvider {

    /**
     * Determine the {@link DataModel} of a Class.
     *
     * @param <T> class type
     * @param clazz class
     * @return data model
     * @throws ModelException thrown if there are problems modeling this class
     */
    public <T> DataModel<T> model(Class<T> clazz) throws ModelException;
}
