package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Set;
import org.geoint.acetate.model.annotation.Model;

/**
 * An ObjectModel is a data model component which is composed of zero or more
 * {@link CompositeModel composite} components.
 *
 * An ObjectModel supports multiple inheritance, inheriting composite components
 * from its parent(s). Despite this flexibility, an ObjectModel must have
 * uniquely-named composite components. If composites have a name collision
 * component model equality will be checked - if they are identical model
 * components the newly found component is discarded. If it's found that the
 * component models are different, the domain model is invalid and a
 * {@link ModelException} must be thrown.
 *
 * @param <T> associated java data type
 */
@Model(name = "object", domainName = "acetate", domainVersion = 1)
public interface ObjectModel<T> extends DataModel<T> {

    /**
     * Operations defined by this object model.
     *
     * @return object operations
     */
    Collection<OperationModel> getOperations();
}
