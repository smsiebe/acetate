package org.geoint.acetate.model;

import org.geoint.acetate.data.Data;

/**
 * Model of a data-bearing component of a Taxonomy.
 * 
 * @param <T> java class representation of the data component
 */
public interface DataModel<T> extends DomainModel{

    /**
     * Create an instance of a Java object that represents the {@link Data}.
     * 
     * @param data
     * @return java representation of this data
     * @throws UnsupportedModelException thrown if the data provided is not 
     * supported by this data model
     * @throws InstantiationException throws if there is a problem instantiating 
     * the java object 
     */
    T getInstance(Data<T> data) 
            throws UnsupportedModelException, InstantiationException;
}
