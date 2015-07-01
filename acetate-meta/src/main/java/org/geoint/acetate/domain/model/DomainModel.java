package org.geoint.acetate.domain.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.domain.annotation.Object;

/**
 * Defines one or more {@link DomainObject components} used to define the types
 * of data with a data model.
 *
 * All DomainModel instances must be immutable and thread-safe.
 */
@Object(name = "domainModel", domainName = "acetate", domainVersion = "1.0-BETA")
public interface DomainModel {

    /**
     * Metamodel attribute name used to define the domain model of the object.
     * <p>
     * Without the domain name defined, the {@link #DEFAULT_DOMAIN_NAME} will be
     * used.
     */
    public static final String META_DOMAIN_NAME = "acetate.domain.name";
    /**
     *
     */
    public static final String META_DOMAIN_VERSION = "acetate.domain.version";

    /**
     * Default domain name used when no domain model is defined.
     *
     */
    public static final String DEFAULT_DOMAIN_NAME = "default";

    /**
     * Default domain version major used if no domain is defined.
     */
    public static final int DEFAULT_DOMAIN_VERSION_MAJOR = 1;
    /**
     * Default domain version minor component used if no domain is defined.
     *
     */
    public static final int DEFAULT_DOMAIN_VERSION_MINOR = 0;
    /**
     * Default domain qualifier component used if no domain is defined.
     */
    public static final VersionQualifier DEFAULT_DOMAIN_VERSION_QUALIFIER
            = VersionQualifier.DEV;

    /**
     * Globally-unique name of the domain model.
     *
     * The name of the domain model, along with its version, is used to uniquely
     * identify a domain model instance and is used to form the domain models
     * unique identifier (though how this is done is implementation specific).
     * <p>
     * Models must identify itself with the same name across versions to be
     * treated as different versions of the same domain model.
     * <p>
     * The name of a domain model is treated as case-insensitive and must only
     * contain ASCII alphanumeric characters or spaces, no special characters.
     *
     * @return name of the data model
     */
    String getName();

    /**
     * Version of the domain model.
     * <p>
     * A domain model version indicates its quality as well as generation.
     *
     * @return domain model version
     */
    MetaVersion getVersion();

    /**
     * Returns an immutable collection containing all the model components
     * within the domain model.
     *
     * @return all components within the domain model
     */
    Collection<ObjectModel> findAll();

    /**
     * Returns a component model by its address.
     *
     * @param componentName domain model unique name of the component
     * @return component model or null if the address does not resolve to a
     * component
     */
    Optional<ObjectModel> find(String componentName);

    /**
     * Returns an immutable collection of domain model components which are
     * decorated with the specified attribute.
     *
     * @param attributeName attribute name
     * @return collection of component models which are decorated with the
     * requested attribute, or an empty collection if not components have the
     * requested attribute
     */
    Collection<ObjectModel> findByAttribute(String attributeName);

}
