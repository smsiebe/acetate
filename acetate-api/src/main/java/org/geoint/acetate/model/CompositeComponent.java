package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.constraint.NotNull;

/**
 * Provides the context of the aggregate(/composite) relationship between
 * components.
 *
 * The constraints, attribute, and codec of a composite override that of the
 * component it defines.
 *
 * @see SimpleCompositeComponent
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
