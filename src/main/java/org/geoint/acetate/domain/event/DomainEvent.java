package org.geoint.acetate.domain.event;

import java.time.ZonedDateTime;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.domain.entity.Entity;

/**
 * A DomainEvent wraps the application event providing event metadata such as
 * commit information, event time, etc.
 *
 *
 * @author steve_siebert
 * @param <T> event class
 * @param <E> entity class
 */
@Entity(name="DomainEvent")
public interface DomainEvent<T, E> extends DomainType<T> {

    String getEventId();

    boolean isCommitted();

    ZonedDateTime getEventTime();

    String getEntityDomain();

    String getEntityVersion();

}
