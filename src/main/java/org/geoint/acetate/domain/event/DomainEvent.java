

package org.geoint.acetate.domain.event;

import java.time.ZonedDateTime;
import java.util.Optional;
import org.geoint.acetate.domain.entity.DomainEntity;
import org.geoint.acetate.domain.DomainType;


/**
 *
 * @author steve_siebert
 * @param <T> event class
 * @param <E> entity class
 */
public interface DomainEvent<T, E> extends DomainType<T> {

    Optional<String> getEventId();
    
    boolean isCommitted();
    
    ZonedDateTime getEventTime();
    
    DomainEntity<E> getEntity();
    
}
