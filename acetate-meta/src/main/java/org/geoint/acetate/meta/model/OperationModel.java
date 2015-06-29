package org.geoint.acetate.meta.model;

import java.util.Map;

/**
 * Model of an Object operation.
 *
 * An object operation is one which meets the following requirements:
 * <ul>
 * <li>non-static</li>
 * <li>has public visibility</li>
 * <li>is not annotated with {@link DoNotModel}</li>
 * </ul>
 *
 * Operations are inherited from {@link ObjectModel#getParents() parent models}.
 *
 */
public interface OperationModel {

    /**
     * Name of the operation.
     *
     * @return operation name
     */
    String getOperationName();

    /**
     * Operation parameter models.
     *
     * @return parameter models
     */
    Map<String, ParameterModel> getParameterModels();

    /**
     * Operation return model.
     *
     * @return return model
     */
    ReturnModel getReturnModel();

}
