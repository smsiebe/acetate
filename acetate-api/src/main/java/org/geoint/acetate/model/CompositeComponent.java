package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.constraint.NotNull;

/**
 * A component of a {@link CompositeComponentModel}, which defines the context
 * and components of the composite model.
 *
 */
public interface CompositeComponent {

    /**
     * Component name which must be CompositeComponentModel (containing
     * component) unique.
     *
     * @return
     */
    @NotNull
    String getLocalName();

}
