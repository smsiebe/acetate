package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.ParameterModel;

/**
 *
 */
final class ImmutableParameterModel implements ParameterModel {

    private final String name;
    private final ObjectModel model;

    public ImmutableParameterModel(String name, ImmutableObjectModel model) {
        this.name = name;
        this.model = model;
    }

    public ImmutableParameterModel(String name,
            DeferredImmutableObjectModel deferred) {
        this.name = name;
        this.model = deferred;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ObjectModel getModel() {
        return model;
    }

}
