package org.geoint.acetate.model;

import java.time.ZonedDateTime;
import java.util.Set;
import org.geoint.acetate.model.annotation.Domain;

/**
 * Model of an event resulting from a change to an {@link EntityModel Entity}
 * instance.
 *
 * Operations which complete successfully return an Event detailing the effects
 * of the operation.
 *
 * @param <E> java data type of the entity event
 */
@Domain(name = "acetate", version = 1)
public interface EventModel<E> extends ComposedModel<E> {

    /**
     * EventModel components from which this model inherits.
     *
     * @return parent models
     */
    Set<EventModel<? super E>> getParents();

    /**
     * EventModel components which inherit from this model.
     *
     * @return specialized models
     */
    Set<EventModel<? extends E>> getSpecialized();

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
