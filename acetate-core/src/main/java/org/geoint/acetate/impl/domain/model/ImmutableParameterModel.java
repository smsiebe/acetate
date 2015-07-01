package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.ParameterModel;

/**
 *
 */
final class ImmutableParameterModel implements ParameterModel {

    private final String name;
    private final ObjectModel model;

    public ImmutableParameterModel(String name, ObjectModel model) {
        this.name = name;
        this.model = model;
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
