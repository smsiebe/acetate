package org.geoint.acetate.model;

import java.util.Optional;
import org.geoint.acetate.model.annotation.attribute.EntityId;
import org.geoint.acetate.model.annotation.attribute.Version;
import org.geoint.acetate.model.annotation.constraint.NotNull;

/**
 * Defines one or more {@link DomainObject components} used to define the types
 * of data with a data model.
 *
 * All DomainModel instances must be immutable and thread-safe.
 */
public interface DomainModel {

    /**
     * Unique domain model identifier.
     *
     *
     * @return unique domain model identifier
     */
    @EntityId
    String getDomainId();

    /**
     * Version of the domain model.
     * <p>
     * A domain model version indicates its quality as well as generation. A
     * domain version of 0 or greater domain model version indicates the domain
     * model is "production", with later generations of a domain model having a
     * version greater than younger generations. A domain model version of -1 or
     * less indicates that the model is "development", or otherwise
     * non-production, and for development version the lowest version is the
     * newest development version.
     * <p>
     * For example:
     * <ul>
     * <li>A production domain model version 12 is newer than a domain model
     * with the same name at version 11. Conversely, it may be said that the
     * model version 11 is <i>older</i> than version 12.</li>
     * <li>A development domain model of version -7 is newer than a domain model
     * with the same name at version -2. Conversely, version -2 is
     * <i>older</i> than version -7.</li>
     * </ul>
     *
     * @return domain model version
     */
    @Version
    @NotNull
    long getVersion();

    /**
     * Quasi-human-readable, globally-unique, name of the domain model.
     *
     * The name of the domain model, along with its version, is used to uniquely
     * identify a domain model and is used to form the domain models unique
     * identifier (though how this is done is unspecified).
     * <p>
     * Models must identify itself with the same name across versions to be
     * related - and other models must not use the same name. It's highly
     * recommended for systems to utilize a model registry whenever possible.
     * <p>
     * The name of a domain model is treated as case-insensitive and must only
     * contain alphanumeric characters, no spaces, special characters, etc.
     *
     * @return name of the data model
     */
    @NotNull
    //TODO add constraint annotations for formatting requirements
    String getName();

    /**
     * Human-readable name of the domain model.
     *
     * @return display name of the domain model
     */
    String getDisplayName();

    /**
     * Optional human-readable description of the data model.
     *
     * @return optional human-readable description of the data model
     */
    Optional<String> getDescription();

    /**
     * Registry containing the component models that make up this domain model.
     *
     * @return components of the data model, in no particular order. A model
     * with no components will return an empty collection.
     */
    ObjectRegistry getComponents();
}
