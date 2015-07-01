package org.geoint.acetate.impl.domain.model;

import java.util.Collections;
import java.util.Map;
import org.geoint.acetate.domain.model.OperationModel;
import org.geoint.acetate.domain.model.ParameterModel;
import org.geoint.acetate.domain.model.ReturnModel;

/**
 *
 * @param <R>
 */
final class ImmutableOperationModel implements OperationModel {

    private final String name;
    private final Map<String, ParameterModel> parameterModels;
    private final ReturnModel returnModel;

    public ImmutableOperationModel(String name,
            Map<String, ParameterModel> parameterModels,
            ReturnModel returnModel) {
        this.name = name;
        this.parameterModels = Collections.unmodifiableMap(parameterModels);
        this.returnModel = returnModel;
    }

    @Override
    public String getOperationName() {
        return name;
    }

    @Override
    public Map<String, ParameterModel> getParameterModels() {
        return parameterModels;
    }

    @Override
    public ReturnModel getReturnModel() {
        return returnModel;
    }

}
