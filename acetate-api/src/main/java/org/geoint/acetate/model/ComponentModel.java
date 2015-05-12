package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.annotation.attribute.ComponentId;

/**
 * A supported data component within a domain model.
 *
 * @param <T> associated java data type
 */
public interface ComponentModel<T> {

    /**
     * Domain model unique component name.
     *
     * @return unique component name
     */
    @ComponentId
    String getComponentName();

    /**
     * Component data type, if known.
     *
     * @return component data type
     */
    Optional<Class<T>> getDataType();

    /**
     * Component models from which this component inherits/specializes.
     *
     * @return inherited (parent) components
     */
    Collection<ComponentModel<?>> getInheritedComponents();

    /**
     * The default context of this component, as defined by the component
     * definition on the domain model.
     *
     * @return default context of the model component
     */
    ComponentContext getDefaultContext();
}
