package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ModelException;
import org.geoint.util.function.Creator;

/**
 *
 * @param <T> model type
 * @param <B> child builder type
 */
public interface DataModelBuilder<T, B extends DataModelBuilder> {

    B alias(String name) throws ModelException;

    B defaultValue (Creator<T> value);
    
    B required (boolean required);
    
    B required ();
    
    
}
