package org.geoint.acetate.model;

import java.time.ZonedDateTime;
import org.geoint.acetate.meta.annotation.Model;

/**
 * Model of an event resulting from a change to an {@link EntityModel Entity}
 * instance.
 *
 * Operations which complete successfully return an Event detailing the effects
 * of the operation.
 *
 * @param <E> java data type of the entity event
 */
@Model(name="event", domainName="acetate", domainVersion=1)
public interface EventModel<E> extends ComposedModel<E> {

    /**
     * Entity event component which defines the time of the event.
     *
     * @return event model component which defines the time of the event
     */
    ValueModel<ZonedDateTime> getCommitTime();

    /**
     * Entity event component which defines the GUID of the entity object for
     * which this event applies.
     *
     * @return event model component containing the related entity object guid
     */
    ValueModel<String> getEntityGuid();

    /**
     * Entity event component which defines the version of the entity object for
     * which the event applies.
     *
     * @return event model component containing the related entity object
     * version
     */
    ValueModel<Long> getEntityVersion();

}
