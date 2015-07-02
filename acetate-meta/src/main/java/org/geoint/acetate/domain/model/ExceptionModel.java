package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Model of a Throwable type.
 *
 */
@Model(name = "Exception Model",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface ExceptionModel {

    String getName();

    String getDomainName();

    MetaVersion getDomainVersion();

}
