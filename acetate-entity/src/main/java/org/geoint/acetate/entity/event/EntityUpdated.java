
package org.geoint.acetate.entity.event;

import org.geoint.acetate.entity.annotation.Event;
import org.geoint.acetate.entity.model.EntityModel;

/**
 *
 */
@Event(domainName=EntityModel.ENTITY_DOMAIN_NAME, 
        domainVersion=EntityModel.ENTITY_DOMAIN_VERSION)
public interface EntityUpdated {

}
