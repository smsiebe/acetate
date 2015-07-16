package org.geoint.acetate.model.common;

import java.time.ZonedDateTime;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.EntityId;
import org.geoint.acetate.domain.annotation.EntityVersion;
import org.geoint.acetate.domain.annotation.Event;
import org.geoint.acetate.domain.model.DataModel;

/**
 *
 */
@Event(name = "abstractEvent", displayName = "Abstract Domain Event",
        domain = DataModel.COMMON_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public abstract class AbstractEvent {

    private final String eventId;
    private final ZonedDateTime commitTime;
    private final String entityType;
    private final String entityId;
    private final String entityVersionApplied;
    private final String newEntityVersion;

    public AbstractEvent(String eventId, ZonedDateTime commitTime,
            String entityType, String entityId,
            String entityVersionApplied, String newEntityVersion) {
        this.eventId = eventId;
        this.commitTime = commitTime;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityVersionApplied = entityVersionApplied;
        this.newEntityVersion = newEntityVersion;
    }

    @Accessor
    @Composite(name = "eventId", displayName = "Event ID")
    public String getEventId() {
        return eventId;
    }

    @Accessor
    @Composite(name = "commitTime", displayName = "Event Commit Time")
    public ZonedDateTime getCommitTime() {
        return commitTime;
    }

    @EntityId
    @Accessor
    @Composite(name = "entityId", displayName = "Entity ID")
    public String getEntityId() {
        return entityId;
    }

    @Accessor
    @Composite(name = "entityType", displayName = "Entity Type")
    public String getEntityType() {
        return entityType;
    }

    @Accessor
    @Composite(name = "appliedEntityVersion", displayName = "Entity Applied Version")
    public String getEntityVersionApplied() {
        return entityVersionApplied;
    }

    @EntityVersion
    @Accessor
    @Composite(name = "newEntityVersion", displayName = "New Entity Version")
    public String getNewEntityVersion() {
        return newEntityVersion;
    }

}
