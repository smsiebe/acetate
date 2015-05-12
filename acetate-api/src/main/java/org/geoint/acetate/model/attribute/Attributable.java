package org.geoint.acetate.model.attribute;

import java.util.Collection;

/**
 * A model component that can have {@link ComponentAttribute attributes}
 * associated with the instance.
 */
public interface Attributable {

    /**
     * Model-defined data attributes.
     *
     * @return model-defined attributes of the component
     */
    Collection<ComponentAttribute> getAttributes();
}
