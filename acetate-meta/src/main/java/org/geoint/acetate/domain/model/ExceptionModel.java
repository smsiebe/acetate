package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Object;

/**
 * Model of a Throwable type.
 *
 */
@Object(name="exception", domainName="acetate", domainVersion="1.0-BETA")
public interface ExceptionModel {

    String getName();

    String getDomainName();

    MetaVersion getDomainVersion();

}
