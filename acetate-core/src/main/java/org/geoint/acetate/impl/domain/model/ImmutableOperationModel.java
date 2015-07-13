package org.geoint.acetate.impl.domain.model;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.entity.model.OperationModel;
import org.geoint.acetate.domain.model.ParameterModel;

/**
 * Model of an objects operation.
 */
final class ImmutableOperationModel implements OperationModel {

    private final String name;
    private final Optional<String> description;
    private final Map<String, ParameterModel> parameterModels;
    private final Optional<ObjectModel> returnModel;
    private final Set<ObjectModel> errorModels;

    public ImmutableOperationModel(String name,
            String description,
            Map<String, ParameterModel> parameterModels,
            ObjectModel returnModel,
            Set<ObjectModel> errorModels) {
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.parameterModels = Collections.unmodifiableMap(parameterModels);
        this.returnModel = Optional.ofNullable(returnModel);
        this.errorModels = errorModels;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return this.description;
    }

    @Override
    public Map<String, ParameterModel> getParameterModels() {
        return parameterModels;
    }

    @Override
    public Optional<ObjectModel> getReturnModel() {
        return returnModel;
    }

    @Override
    public Set<ObjectModel> getErrorModels() {
        return errorModels;
    }

}
