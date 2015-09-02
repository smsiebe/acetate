package org.geoint.acetate;

import java.time.ZonedDateTime;

/**
 * A DomainEvent wraps the application event providing event metadata such as
 * commit information, event time, etc.
 *
 *
 * @author steve_siebert
 * @param <T> event class
 * @param <E> entity class
 */
public interface DomainEvent<T, E> extends DomainType<T> {

    String getEventId();

    boolean isCommitted();

    ZonedDateTime getEventTime();

    String getEntityDomain();

    String getEntityVersion();

    public static boolean isEvent(DomainType t) {
        return DomainEvent.class.isAssignableFrom(t.getClass());
    }
}
