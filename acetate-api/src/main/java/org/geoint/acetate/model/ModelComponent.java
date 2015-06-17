package org.geoint.acetate.model;

import java.util.Optional;
import org.geoint.acetate.model.annotation.Model;
import org.geoint.acetate.model.attribute.Attributed;

/**
 * A component of a domain data model.
 *
 *
 */
@Model(name="", domainName="acetate", domainVersion=1)
public interface ModelComponent extends Attributed {

    /**
     * The domain model of which this component belongs.
     *
     * @return domain model containing this component
     */
    DomainModel getDomainModel();

    /**
     * Model-model unique name of the component.
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
