package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import gov.ic.geoint.acetate.bind.DataType;

/**
 * Immutable data component of a model.
 *
 * @param <T> data value type
 */
public class DataComponentImpl<T> implements ComponentModel<T> {

    private final DataType<T> type;
    private final Collection<ComponentConstraint<T>> constraints;
    private final Collection<ComponentAttribute> attributes;

    public DataComponentImpl(DataType<T> type,
            Collection<ComponentConstraint<T>> constraints,
            Collection<ComponentAttribute> attributes) {
        this.type = type;
        this.constraints = defend(constraints);
        this.attributes = defend(attributes);
    }

    @Override
    public DataType<T> getType() {
        return type;
    }

    @Override
    public Collection<ComponentConstraint<T>> getConstraints() {
        return constraints;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return attributes;
    }

    private <T> Collection<T> defend(Collection<T> orig) {
        return Collections.unmodifiableCollection(
                new ArrayList<>(orig));
    }
}
