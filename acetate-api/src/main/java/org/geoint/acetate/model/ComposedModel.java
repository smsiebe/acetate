package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.annotation.Domain;

/**
 * A data model component which is composed from other data model components
 * (composites).
 *
 * @param <T> java class representation of component
 */
@Domain(name = "acetate", version = 1)
public interface ComposedModel<T> extends DataModel<T> {

    /**
     * Composite data model components that make up this model.
     *
     * @return composite models for this component
     */
    Collection<ContextualModel> getComposites();
}
