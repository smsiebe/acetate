package org.geoint.acetate.model.attribute;

import java.util.Collection;
import org.geoint.acetate.model.Inheritable;

/**
 * A model component that can have {@link ComponentAttribute attributes}
 * associated with the instance.
 */
public interface Attributable extends Inheritable{

    /**
     * Model-defined data attributes.
     *
     * @return model-defined attributes of the component
     */
    Collection<? extends ComponentAttribute> getAttributes();
}
