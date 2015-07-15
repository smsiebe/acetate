package org.geoint.acetate.domain.model;

import org.geoint.acetate.model.common.Version;
import org.geoint.acetate.data.Data;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.Query;

/**
 *
 */
@Entity(domain = DomainModel.ACETATE_DOMAIN_NAME,
        name = "taxonomy", displayName = "Taxonomy",
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public interface Taxonomy {

    /**
     * "Short" name of the model.
     * <p>
     * This is the name used to programmatically address the domain and domain
     * models.
     *
     * @return globally unique domain model name
     */
    @Composite(name = "domainName", displayName = "Domain Name")
    String getName();

    /**
     * Version of the domain model.
     *
     * @return domain model version
     */
    @Composite(name = "version", displayName = "Domain Version")
    Version getVersion();

    /**
     * Returns an immutable collection containing all the model components
     * within this taxonomy.
     *
     * @return all components within the taxonomy
     */
    @Composite(name = "models", displayName = "Domain Model Components")
    Collection<DomainModel> getModels();

    /**
     * Returns a component model by its name.
     *
     * @param modelName taxonomy unique name of the component
     * @return component model or null if the address does not resolve to a
     * component
     */
    @Query(name = "findByModelName")
    Optional<DomainModel> find(String modelName);

//    /**
//     * Returns the data model for the provided class, or null if the class is
//     * not modeled as a domain object.
//     *
//     * @param <T> data type
//     * @param type
//     * @return data model or null
//     */
//    //@DoNotModel
//    <T> Optional<DataModel<T>> find(Class<T> type);
//
//    /**
//     * Returns the {@link Data} representation of the provided object instance.
//     *
//     * @param <T>
//     * @param obj
//     * @return modeled data for this object
//     */
//    //@DoNotModel
//    <T> Data<T> find(T obj);
}
