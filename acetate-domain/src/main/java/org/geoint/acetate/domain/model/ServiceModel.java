package org.geoint.acetate.domain.model;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.annotation.MultiComposite;
import org.geoint.acetate.domain.annotation.Service;

/**
 * Model of a domain service.
 *
 * @param <S> java service object type
 * @see Service
 */
@Model(name = "serviceModel", displayName = "Domain Service Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface ServiceModel<S> extends DataModel<S> {

    @Accessor(name = "getOperations")
    @MultiComposite(name = "operations", itemName = "operation")
    Collection<OperationModel> getOperations();
}
