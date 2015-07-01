package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Object;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Model of a Throwable type.
 *
 */
@Object(name = "exception",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface ExceptionModel {

    String getName();

    String getDomainName();

    MetaVersion getDomainVersion();

}
