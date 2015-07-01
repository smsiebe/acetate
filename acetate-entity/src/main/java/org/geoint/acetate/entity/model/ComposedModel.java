package org.geoint.acetate.entity.model;

import java.util.Collection;
import java.util.Set;
import org.geoint.acetate.meta.annotation.Model;

/**
 * A data model component which is composed from other data model components
 * (composites).
 *
 * @param <T> java class representation of component
 */
@Model(name = "composed", domainName = "acetate", domainVersion = 1)
public interface ComposedModel<T> extends DataModel<T> {

    /**
     * EventModel components from which this model inherits.
     *
     * @return parent models
     */
    Set<ComposedModel<? super T>> getParents();

    /**
     * EventModel components which inherit from this model.
     *
     * @return specialized models
     */
    Set<ComposedModel<? extends T>> getSpecialized();

    /**
     * Composite data model components that make up this model.
     *
     * @return composite models for this component
     */
    Collection<ContextualModel> getComposites();
}
