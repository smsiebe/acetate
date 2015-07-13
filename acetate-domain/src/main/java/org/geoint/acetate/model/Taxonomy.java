package org.geoint.acetate.model;

import org.geoint.acetate.data.Data;
import java.util.Collection;
import java.util.Optional;

/**
 *
 */
public interface Taxonomy {

    /**
     * Returns an immutable collection containing all the model components
     * within this taxonomy.
     *
     * @return all components within the taxonomy
     */
    Collection<DomainModel> findAll();

    /**
     * Returns a component model by its name.
     *
     * @param modelName taxonomy unique name of the component
     * @return component model or null if the address does not resolve to a
     * component
     */
    Optional<DomainModel> find(String modelName);

    /**
     * Returns the data model for the provided class, or null if the class is
     * not modeled as a domain object.
     *
     * @param <T> data type
     * @param type
     * @return data model or null
     */
    <T> Optional<DataModel<T>> find(Class<T> type);

    /**
     * Returns the {@link Data} representation of the provided object 
     * instance. 
     * 
     * @param <T>
     * @param obj
     * @return modeled data for this object
     */
    <T> Data<T> find (T obj);
}
