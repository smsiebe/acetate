package org.geoint.acetate.model;

import java.util.Collection;

/**
 * A context-specific definition of a data type within a data model.
 *
 * @param <T> associated java data type
 */
public interface DataComponent<T> {

    /**
     * Component data type.
     *
     * @return component data type
     */
    DataType<T> getType();

    /**
     * Data constraints defined by the data model for this component.
     *
     * @return model-defined constraints for this component
     */
    Collection<DataConstraint<T>> getConstraints();

    /**
     * Model-defined data attributes.
     *
     * @return model-defined attributes of the component
     */
    Collection<DataAttribute> getAttributes();
}
