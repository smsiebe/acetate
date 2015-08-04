

package org.geoint.acetate.domain;


/**
 *
 * @author steve_siebert
 */
public interface DomainEvent<T> extends DomainType<T> {

    DomainEntity getEntity();
    
}
