package org.geoint.acetate.impl.model.reflect;

import java.util.Collection;
import org.geoint.acetate.model.ClassModel;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.ValueConstraint;
import org.geoint.acetate.model.ValueConstraintException;

/**
 *
 * @param <T> class type
 */
public class ReflectClassModel<T> implements ClassModel<T> {

    private String id;
    private Collection<ComponentModel<?>> components;
    private ValueConstraint[] constraints;

    @Override
    public Collection<ComponentModel<?>> getComponents() {

    }

    @Override
    public String getId() {

    }

    @Override
    public Collection<ValueConstraint<T>> getConstraints() {

    }

    @Override
    public void validate(T data) throws ValueConstraintException {
        //TODO
        throw new UnsupportedOperationException();
    }

}
