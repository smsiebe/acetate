package org.geoint.acetate.meta.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Object metadata as identified by the acetate metamodel framework.
 *
 */
public interface ObjectModel {

//    /**
//     * Java object this meta models.
//     *
//     * @return java type
//     */
//    Class<T> getObjectType();
    /**
     * Domain model unique name of the object.
     *
     * @return
     */
    String getName();

    String getDomainName();

    MetaVersion getDomainVersion();

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
     * Object operations that were declared directly by this object model.
     *
     * @return object operation models declared by this model
     */
    Collection<OperationModel> getDeclaredOperations();

    /**
     * All object operations, including those inherited from parent object
     * models.
     *
     * @return all object operation models
     */
    Collection<OperationModel> getOperations();

    /**
     * Object model classes from which this model extends.
     *
     * @return parent object models
     */
    Collection<ObjectModel> getParents();

    /**
     * Object model classes that extends this model.
     *
     * @return specialized types of this model
     */
    Collection<ObjectModel> getSpecialized();

}
