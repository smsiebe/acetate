package org.geoint.acetate.impl.model.entity;

import java.util.Collection;
import org.geoint.acetate.impl.model.InMemoryDomainModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 *
 */
public final class ImmutableDomainModel extends InMemoryDomainModel {

    public ImmutableDomainModel(String name,
            long version,
            String displayName,
            String description,
            Collection<ModelComponent> components)
            throws ModelException {
        super(name, version, displayName, description, components);
    }

    /**
     * Constructs instances of ImmutableDomainModel containing the provided
     * components.
     *
     * This method intentionally implements the {@link DomainModelFactory}
     * functional interface so that it may be passed by reference.
     *
     * @param components
     * @return models
     * @throws ModelException thrown if a domain model is invalid
     */
    public static Collection<DomainModel> fromComponents(
            Collection<ModelComponent> components)
            throws ModelException {

    }
}
