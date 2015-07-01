package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Object;

/**
 * Model of an Operation parameter.
 *
 */
@Object(name="operationParameter",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface ParameterModel {

    String getName();

    ObjectModel getModel();

}
