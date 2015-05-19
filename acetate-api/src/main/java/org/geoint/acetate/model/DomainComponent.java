package org.geoint.acetate.model;

import java.util.Optional;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 * A component of a domain model.
 *
 * @see DomainObject
 * @see DomainEntityEvent
 * @see DomainObjectOperation
 * @see DomainEntityObject
 * @see DomainAggregateObject
 * @see DomainCompositeObject
 *
 * @param <T> java type of the domain model component
 */
public interface DomainComponent<T> {

    /**
     * The DomainModel this component is a member of.
     *
     * @return components domain model
     */
    DomainModel getDomainModel();

    /**
     * Path of the domain model component within the context of the domain
     * model.
     *
     * @return components contextual path
     */
    ModelContextPath getPath();

    /**
     * Domain model unique name of the component.
     *
     * @return domain model unique component name
     */
    String getName();

    /**
     * Optional component description.
     *
     * @return optional description
     */
    Optional<String> getDescription();

}
