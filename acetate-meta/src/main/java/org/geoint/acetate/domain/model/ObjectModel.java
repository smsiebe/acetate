package org.geoint.acetate.domain.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Model metadata as identified by the acetate metamodel framework.
 *
 */
@Model(name = "object",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface ObjectModel {

    public static final String META_OBJECT_NAME = "acetate.object.name";

    /**
     * Model model unique name of the object.
     *
     * @return
     */
    String getName();

    /**
     * Model model name of the object.
     *
     * @return domain model name
     */
    String getDomainName();

    /**
     * Model model version of the object.
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
     * Model operations that were declared directly by this object model.
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
     * Model model classes from which this model extends.
     *
     * @return parent object models
     */
    Collection<ObjectModel> getParents();

    /**
     * Model model classes that extends this model.
     *
     * @return specialized types of this model
     */
    Collection<ObjectModel> getSpecialized();

}
