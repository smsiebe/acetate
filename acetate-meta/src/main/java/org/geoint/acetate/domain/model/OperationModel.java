package org.geoint.acetate.domain.model;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
@Model(name = "Operation Model",
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
    String getName();

    /**
     * Optional operation description.
     *
     * @return optional operation description
     */
    Optional<String> getDescription();

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
    Optional<ObjectModel> getReturnModel();

    /**
     * Potential error objects that can be return (thrown) if execution of the
     * operation did not successfully complete.
     *
     * @return potential exceptions that could be thrown if operation did not
     * succeed
     */
    Set<ObjectModel> getErrorModels();

}
