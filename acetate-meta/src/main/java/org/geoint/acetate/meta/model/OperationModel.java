package org.geoint.acetate.meta.model;

import java.util.Map;

/**
 * Model of an Object operation.
 *
 * @param <R> return type of the operation
 */
public interface OperationModel<R> {

    String getOperationName();

    Map<String, ParameterModel<?>> getParameterModels();

    ReturnModel<R> getReturnModel();

}
