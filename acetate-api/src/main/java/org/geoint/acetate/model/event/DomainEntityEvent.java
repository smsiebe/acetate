package org.geoint.acetate.model.event;

import java.time.ZonedDateTime;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.EntityModel;
import org.geoint.acetate.model.ObjectModel;

/**
 * A specialized model component within a domain model which
 * <i>relates to</i> a change to an {@link EntityModel Entity} instance.
 *
 * @param <T> object representation of the domain event
 * @param <E> domain entity object type
 */
public interface DomainEntityEvent<T, E> extends ModelComponent<T> {

    /**
     * Entity Event component which defines its globally unique identifier.
     *
     * Every event instance will have a globally unique identifier.
     *
     * @return the aggregate/composite domain object which is the globally
     * unique identifier of the entity
     */
    ObjectModel<String> getEventGuidComponent();

    /**
     * Entity event component which defines the time of the event.
     *
     * @return event model component which defines the time of the event
     */
    ObjectModel<ZonedDateTime> getEventTimeComponent();

    /**
     * Entity event component which defines the GUID of the entity object for
     * which this event applies.
     *
     * @return event model component containing the related entity object guid
     */
    ObjectModel<String> getEntityGuidComponent();

    /**
     * Entity event component which defines the version of the entity object for
     * which the event applies.
     *
     * @return event model component containing the related entity object
     * version
     */
    ObjectModel<Long> getEntityVersionComponent();

    /**
     * Model of related domain entity object.
     *
     * @return domain entity object model
     */
    EntityModel<E> getEntityModel();

}
