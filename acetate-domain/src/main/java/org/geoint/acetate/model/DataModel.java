package org.geoint.acetate.model;

import org.geoint.acetate.meta.annotation.Model;
import org.geoint.acetate.model.constraint.Constrained;

/**
 * Data-bearing domain model component.
 * 
 * @param <T> java class representation of component
 */
@Model(name="data", domainName="acetate", domainVersion=1)
public interface DataModel<T> extends ModelComponent, Constrained {

    /**
     * Java class representation of the data held by this component.
     * 
     * @return java class of the data component
     */
    Class<T> getDataClass();
    
}
