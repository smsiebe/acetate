package org.geoint.acetate.domain.model;

import java.util.Map;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.DoNotModel;

/**
 * Model of an Model operation.
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
@Model(name="operation",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
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
