package org.geoint.acetate.model.attribute;

import java.util.Collection;
import java.util.stream.Collectors;

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
    
    default Collection<ComponentAttribute> getAttributes(
            Class<? extends ComponentAttribute> type) {
        return getAttributes().stream()
                .filter((a) -> a.getClass().equals(type))
                .collect(Collectors.toList());
    }
    
    default boolean hasAttribute(
            Class<? extends ComponentAttribute> attributeType) {
        return getAttributes().stream()
                .anyMatch((a) -> a.getClass().equals(attributeType));
    }
}
