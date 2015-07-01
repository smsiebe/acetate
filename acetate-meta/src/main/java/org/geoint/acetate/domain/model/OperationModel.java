package org.geoint.acetate.domain.model;

import java.util.Map;
import org.geoint.acetate.domain.annotation.Object;
import org.geoint.acetate.domain.annotation.DoNotModel;

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
@Object(name="operation", domainName="acetate", domainVersion="1.0-BETA")
public interface OperationModel {

    public static final String META_OPERATION_NAME = "acetate.operation.meta";
    public static final String META_OPERATION_DESCRIPTION = "acetate.operation.description";
    
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
