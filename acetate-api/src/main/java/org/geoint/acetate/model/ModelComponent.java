package org.geoint.acetate.model;

import java.util.Optional;
import org.geoint.acetate.model.attribute.Attributable;

/**
 * A data-defining component of a domain model.
 *
 * A domain component defines a piece of data within a domain model. This may be
 * a primitive "field" of an object, a complex object composed of other objects
 * and with rich aggregation relationships, or somewhere in between.
 *
 * @param <T> java type which this domain component may be serialized into
 */
public interface ModelComponent<T> extends Attributable {

    /**
     * The DomainModel this component is a member of.
     *
     * @return components domain model
     */
    DomainModel getDomainModel();

    /**
     * Unique component address within the domain model.
     *
     * @return components contextual address
     */
    ModelAddress getAddress();

    /**
     * Optional component description.
     *
     * @return optional description
     */
    Optional<String> getDescription();

}
