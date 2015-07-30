
package org.geoint.acetate.model;

import org.geoint.acetate.domain.annotation.DomainDataType;

/**
 *
 */
public interface DomainOperation {

    DomainDataType execute(DomainEntity entity, DomainDataType... parameters);
    
}
