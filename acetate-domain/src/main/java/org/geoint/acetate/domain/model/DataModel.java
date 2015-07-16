package org.geoint.acetate.domain.model;

import java.util.Optional;
import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.model.common.VersionQualifier;
import org.geoint.acetate.model.common.Version;

/**
 * A model component of a Taxonomy.
 * 
 * @param <T> java type of the model instance
 */
@Model(name = "model", displayName = "Domain Model Component",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface DataModel<T> {

    /**
     * The domain model name used by the acetate domain model metamodel.
     */
    public static final String ACETATE_DOMAIN_NAME = "acetate.domain";

    /**
     * Domain model name of commmon data model types.
     */
    public static final String COMMON_DOMAIN_NAME = "acetate.model.common";

    /**
     * The current version of the acetate domain model metamodel.
     *
     */
    public static final String ACETATE_DOMAIN_VERSION = "1.0-BETA";

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
     * Globally-unique, case-insensitive, name of the domain model.
     *
     * The name of the domain model, along with its version, is used to uniquely
     * identify a domain model instance and is used to form the domain models
     * unique identifier (though how this is done is implementation specific).
     * <p>
     * Models must identify itself with the same name across versions to be
     * treated as different versions of the same domain model.
     *
     * @return name of the data model
     */
    @Composite(name = "domainName", displayName = "Domain Name")
    String getDomainName();

    /**
     * Domain version this model was first added.
     * <p>
     * A domain model version indicates its quality as well as generation.
     *
     * @return domain model version
     */
    @Composite(name = "version", displayName = "Start Version")
    Version getVersion();

    /**
     * Specific, unique, name of the model component.
     *
     * @return unique model component name
     */
    @Composite(name = "name", displayName = "Model Name")
    String getName();

    /**
     * Name of the model component used for human consumption.
     *
     * @return human-readable model component name
     */
    @Composite(name = "displayName", displayName = "Display Name")
    String getDisplayName();

    /**
     * Optional domain description.
     *
     * @return optional description
     */
    @Composite(name = "description", displayName = "Description")
    Optional<String> getDescription();

}
