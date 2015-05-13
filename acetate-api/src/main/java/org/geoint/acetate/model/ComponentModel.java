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
     * Component behavior/operations.
     *
     * @return component operations or empty collection if no behavior is found
     * on the component
     */
    Collection<? extends ComponentOperation> getOperations();

    /**
     * Component models from which this component inherits/specializes.
     *
     * @return inherited (parent) components
     */
//    Collection<ComponentModel<? super T>> getInheritedComponents();

    /**
     * The default context of this component, as defined by the component
     * definition on the domain model.
     *
     * @return default context of the model component
     */
    ComponentContext getContext();
}
