package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.Model;

/**
 * Models a special object type that records changes to an Entity instance.
 *
 * @param <T>
 */
@Model(name = "eventModel", displayName = "Event Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface EventModel<T> extends CompositeModel<T> {

    @Composite(name = "entityIdName")
    String getEntityIdName();

    @Composite(name = "appliedVersionName")
    String getAppliedEntityVersionName();

    @Composite(name = "newVersionName")
    String getNewVersionName();

    //@DoNotModel
    String getEntityId(T obj);

    //@DoNotModel
    String getAppliedEntityVersion(T obj);

    //@DoNotModel
    String getNewEntityVersion(T obj);
}
