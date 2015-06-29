package org.geoint.acetate.model;

import org.geoint.acetate.impl.meta.model.DomainId;
import java.util.Optional;
import org.geoint.acetate.model.annotation.Model;
import org.geoint.acetate.model.attribute.Attributed;

/**
 * A component of a domain data model.
 *
 *
 */
@Model(name = "component", domainName = "acetate", domainVersion = 1)
public interface ModelComponent extends Attributed {

    /**
     * The domain model of which this component belongs.
     *
     * @return domain model containing this component
     */
    DomainId getDomainId();

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

    /**
     * Hierarchically visits each model component.
     *
     * @param visitor model visitor callback
     */
    void visit(ModelVisitor visitor);
}
