package org.geoint.acetate.domain.model;

import org.geoint.acetate.model.common.VersionQualifier;
import org.geoint.acetate.model.common.Version;

/**
 * A model component of a Taxonomy.
 */
public interface DomainModel {

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
    String getDomainName();

    /**
     * Version of the domain model.
     * <p>
     * A domain model version indicates its quality as well as generation.
     *
     * @return domain model version
     */
    Version getModelVersion();

    /**
     * Specific, unique, name of the model component.
     *
     * @return unique model component name
     */
    String getModelName();

    /**
     * Name of the model component used for human consumption.
     *
     * @return human-readable model component name
     */
    String getDisplayName();

}
