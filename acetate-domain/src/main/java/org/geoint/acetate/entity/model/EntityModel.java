package org.geoint.acetate.entity.model;

import java.util.Collection;
import org.geoint.acetate.model.ObjectModel;

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
public interface EntityModel<T> extends ObjectModel<T> {

    String getId(T entity);

    String getVersion(T entity);

    Collection<OperationModel> getEntityOperations();
}
