package org.geoint.acetate.metamodel;

import org.geoint.acetate.bind.DataBinder;

/**
 * Metadata model of a data item (an object).
 */
public interface DataModel<F> extends StructuredModel {

    /**
     * Binds a java object to this model.
     *
     * @param instance object to bind against this model
     * @return binding object
     */
    public DataBinder<F> bind(F instance);

}
