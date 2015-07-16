package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Composite;
import org.geoint.acetate.domain.annotation.Model;

/**
 * Models the special Entity data type.
 * <p>
 * An Entity instance represents an object of the domain model which must be
 * tracked individually. Changes to an Entity is done through its
 * {@link OperationModel operations}, which on success of change, returns an
 * {@link EventModel event} that describes the changes.
 *
 * @param <T>
 */
@Model(name = "entityModel", displayName = "Entity Model",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface EntityModel<T> extends ObjectModel<T> {

    @Composite(name = "idName")
    String getIdComponentName();

    @Composite(name = "versionName")
    String getVersionComponentName();

    //@DoNotModel
    String getId(T entity);

    //@DoNotModel
    String getVersion(T entity);

}
