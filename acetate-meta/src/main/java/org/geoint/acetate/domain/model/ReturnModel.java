package org.geoint.acetate.domain.model;

import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.domain.annotation.Model;

/**
 * Models what may be returned from the execution of an {@link OperationModel
 * operation}.
 *
 */
@Model(name = "operationReturn",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface ReturnModel {

    /**
     * Model of the returned value upon successful execution of the operation.
     *
     * @return return value upon successful execution of the operation, which
     * may be null (indicating the return type is VOID)
     */
    Optional<ObjectModel> getModel();

    /**
     * Potential exceptions that can be thrown if execution of the operation did
     * not successfully complete.
     *
     * @return potential exceptions that could be thrown if operation did not
     * succeed
     */
    Set<ExceptionModel> getExceptions();
}
