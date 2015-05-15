package org.geoint.acetate.model;

import java.util.Optional;

/**
 *
 */
public interface ModelComponent {

    /**
     * The DomainModel this component is a member of.
     *
     * @return components domain model
     */
    DomainModel getDomainModel();

    /**
     * Domain model context path.
     *
     * @return components contextual path
     */
    ModelContextPath getPath();

    /**
     * Name of the model component.
     *
     * @return component name
     */
    String getName();

    /**
     * Optional component description.
     *
     * @return optional description
     */
    Optional<String> getDescription();

}
