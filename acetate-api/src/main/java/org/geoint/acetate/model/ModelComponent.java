package org.geoint.acetate.model;

import java.util.Optional;

/**
 *
 */
public interface ModelComponent  {

    ComponentPath getPath();

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
