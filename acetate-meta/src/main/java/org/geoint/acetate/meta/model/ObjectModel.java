package org.geoint.acetate.meta.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Object metadata as identified by the acetate metamodel framework.
 *
 * @param <T> java class this object meta is models
 */
public interface ObjectModel<T> {

    /**
     * Java object this meta models.
     *
     * @return java type
     */
    Class<T> getObjectType();

    /**
     * Metamodel attributes assigned to this object model.
     *
     * @return attributes defined by metamodels
     */
    Map<String, String> getAttributes();

    /**
     * Retrieve a specific metamodel attribute.
     *
     * @param attributeName
     * @return metamodel attribute value or null if attribute was not set
     */
    Optional<String> getAttribute(String attributeName);

    /**
     * Object operations.
     *
     * @return object operation meta
     */
    Collection<OperationModel> getOperations();

    /**
     * Object model classes from which this model extends.
     *
     * @return parent object models
     */
    Collection<ObjectModel<? super T>> getParents();

    /**
     * Object model classes that extends this model.
     *
     * @return specialized types of this model
     */
    Collection<ObjectModel<? extends T>> getSpecialized();

}
