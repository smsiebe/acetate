package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.ParameterModel;

/**
 *
 */
public final class ImmutableParameterModel<T> implements ParameterModel<T> {

    private final String name;
    private final ImmutableObjectModel<T> model;

    public ImmutableParameterModel(String name, ImmutableObjectModel<T> model) {
        this.name = name;
        this.model = model;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ObjectModel<T> getModel() {
        return model;
    }

}
