package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Domain;

/**
 * Data-bearing domain model component.
 * 
 * @param <T> java class representation of component
 */
@Domain(name = "acetate", version = 1)
public interface DataModel<T> extends ModelComponent {

    /**
     * Java class representation of the data held by this component.
     * 
     * @return java class of the data component
     */
    Class<T> getDataClass();
    
}
