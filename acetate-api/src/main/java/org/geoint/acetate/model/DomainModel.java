package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.annotation.attribute.ComponentId;
import org.geoint.acetate.model.annotation.attribute.Version;
import org.geoint.acetate.model.annotation.constraint.NotNull;

/**
 * Defines one or more {@link ComponentModel components} used to define the
 * types of data with a data model.
 *
 * All DomainModel instances must be immutable and thread-safe.
 */
public interface DomainModel {

    /**
     * Unique domain model identifier derived from the domain model name and
     * version.
     *
     * Every domain model version is unique, but is related by its domain name.
     *
     * @return unique domain model identifier
     */
    @ComponentId
    String getDomainId();

    /**
     * Version of the domain model.
     *
     * The version is used to generate the domain model ComponentId.
     *
     * @return domain model version
     */
    @Version
    @NotNull
    long getVersion();

    /**
     * Human-readable, globally-unique, name of the domain model.
     *
     * The name of the domain model, along with its version, is used to uniquely
 identify a domain model (used to form its the ComponentId of the domain model).
 Models must identify itself with the same name across versions to be
 related - and other models must not use the same name. It's highly
 recommended for systems to utilize a model registry whenever possible.
     *
     * @return name of the data model
     */
    @NotNull
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
     * Model components that comprise the domain model.
     *
     * @return components of the data model, in no particular order. A model
     * with no components will return an empty collection.
     */
    Collection<ComponentModel> getComponents();

}
