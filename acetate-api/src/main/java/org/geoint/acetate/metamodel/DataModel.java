package org.geoint.acetate.metamodel;

import org.geoint.acetate.bind.BoundData;

/**
 * Metadata model of a data item (an object).
 * 
 * Data models must be immutable.
 * 
 * @param <D> class this models
 */
public interface DataModel<D>  {

    /**
     * Binds a java object to this model.
     *
     * 
     * @param instance object to bind against this model
     * @return bound data object
     */
    public BoundData<D> bind(D instance);
    
    /**
     * The field model for an alias name.
     * 
     * @param alias absolute field name
     * @return model for the field, if no field returns null
     */
    FieldModel<?,?> getField(String alias);
    
}
