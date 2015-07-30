
package org.geoint.acetate.model;

/**
 *
 * @param <T>
 */
public interface DomainEntity<T> extends DomainType<T> {

    String getEntityId();
    
    String getEntityVersion();
    
}
