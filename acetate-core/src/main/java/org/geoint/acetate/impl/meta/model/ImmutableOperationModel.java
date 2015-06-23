
package org.geoint.acetate.impl.meta.model;

import java.util.Collections;
import java.util.Map;
import org.geoint.acetate.meta.model.OperationModel;
import org.geoint.acetate.meta.model.ParameterModel;
import org.geoint.acetate.meta.model.ReturnModel;

/**
 *
 */
public final class ImmutableOperationModel<R> implements OperationModel<R>{

    private final String name;
    private final Map<String, ParameterModel<?>> parameterModels;
    private final ReturnModel returnModel;

    public ImmutableOperationModel(String name, 
            Map<String, ImmutableParameterModel<?>> parameterModels, 
            ImmutableReturnModel returnModel) {
        this.name = name;
        this.parameterModels = Collections.unmodifiableMap(parameterModels);
        this.returnModel = returnModel;
    }
    
    @Override
    public String getOperationName() {
        return name;
    }

    @Override
    public Map<String, ParameterModel<?>> getParameterModels() {
        return parameterModels;
    }

    @Override
    public ReturnModel<R> getReturnModel() {
        return returnModel;
    }

}
