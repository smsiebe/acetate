package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.constraint.NotNull;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.constraint.Constrainable;

/**
 * A component of a {@link CompositeComponentModel}, which defines the context
 * and components of the composite model.
 *
 * The constraints, attribute, and codec of a composite override that of the
 * component it defines.
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
