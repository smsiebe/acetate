package org.geoint.acetate.model;

import java.util.Optional;
import org.geoint.acetate.model.attribute.Attributable;

/**
 * A component of a domain data model.
 *
 * A domain component defines a piece of of a domain model.
 *
 * @see ObjectModel
 * @see ValueModel
 */
public interface ModelComponent extends Attributable {

//    /**
//     * The DomainModel this component is a member of.
//     *
//     * @return components domain model
//     */
//    DomainModel getDomainModel();
    /**
     * Unique component address within the domain model.
     *
     * @return components contextual address
     */
    ComponentAddress getAddress();

    /**
     * Domain-model unique name of the component.
     *
     * @return unique component name
     */
    String getName();

    /**
     * Optional component description.
     *
     * @return optional description
     */
    Optional<String> getDescription();

}
