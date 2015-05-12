package org.geoint.acetate.model;

import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.attribute.ComponentAttribute;
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
    String getName();

    /**
     * Component data type, if known.
     *
     * @return component data type
     */
    Optional<Class<T>> getDataType();

    /**
     * Data constraints defined by the data model for this component.
     *
     * @return model-defined constraints for this component
     */
    Collection<ComponentConstraint> getConstraints();

    /**
     * Model-defined data attributes.
     *
     * @return model-defined attributes of the component
     */
    Collection<ComponentAttribute> getAttributes();
}
