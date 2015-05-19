package org.geoint.acetate.model;

import java.util.Optional;

/**
 * A component of a domain model.
 *
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
     * Optional component description.
     *
     * @return optional description
     */
    Optional<String> getDescription();

}
