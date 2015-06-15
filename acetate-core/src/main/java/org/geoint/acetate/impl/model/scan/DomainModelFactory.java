package org.geoint.acetate.impl.model.scan;

import java.util.Collection;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 *
 */
@FunctionalInterface
public interface DomainModelFactory {

    /**
     * Create domain model(s) from components.
     *
     * @param components model components
     * @return domain models
     * @throws ModelException thrown if a domain model is invalid based on
     * components
     */
    Collection<DomainModel> fromComponents(Collection<ModelComponent> components)
            throws ModelException;
}
