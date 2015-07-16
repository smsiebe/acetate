
package org.geoint.acetate.event;

import org.geoint.acetate.domain.annotation.Event;
import org.geoint.acetate.domain.model.DataModel;

/**
 *
 */
@Event(name="domainRegistered", displayName="Domain Registered",
        domain=DataModel.ACETATE_DOMAIN_NAME,
        version=DataModel.ACETATE_DOMAIN_VERSION)
public class DomainRegistered {

   
    private final String domainId;
    private final String domainName;
    private final Version domainVersion;
    
}
