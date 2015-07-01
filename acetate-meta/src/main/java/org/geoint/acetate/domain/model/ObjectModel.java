package org.geoint.acetate.domain.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.domain.annotation.Object;

/**
 * Object metadata as identified by the acetate metamodel framework.
 *
 */
@Object(name="object", domainName="acetate", domainVersion="1.0-BETA")
public interface ObjectModel {

    public static final String META_OBJECT_NAME = "acetate.object.name";

    /**
     * Object model unique name of the object.
     *
     * @return
     */
    String getName();

    /**
     * Object model name of the object.
     * 
     * @return domain model name
     */
    String getDomainName();

    /**
     * Object model version of the object.
     * 
     * @return domain model version
     */
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
