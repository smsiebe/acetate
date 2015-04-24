package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.geoint.acetate.model.DataAttribute;
import org.geoint.acetate.model.DataComponent;
import org.geoint.acetate.model.DataConstraint;
import org.geoint.acetate.model.DataType;

/**
 * Immutable data component of a model.
 *
 * @param <T> data value type
 */
public class DataComponentImpl<T> implements DataComponent<T> {

    private final DataType<T> type;
    private final Collection<DataConstraint<T>> constraints;
    private final Collection<DataAttribute> attributes;

    public DataComponentImpl(DataType<T> type,
            Collection<DataConstraint<T>> constraints,
            Collection<DataAttribute> attributes) {
        this.type = type;
        this.constraints = defend(constraints);
        this.attributes = defend(attributes);
    }

    @Override
    public DataType<T> getType() {
        return type;
    }

    @Override
    public Collection<DataConstraint<T>> getConstraints() {
        return constraints;
    }

    @Override
    public Collection<DataAttribute> getAttributes() {
        return attributes;
    }

    private <T> Collection<T> defend(Collection<T> orig) {
        return Collections.unmodifiableCollection(
                new ArrayList<>(orig));
    }
}
