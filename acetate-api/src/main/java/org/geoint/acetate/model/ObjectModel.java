package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Set;
import org.geoint.acetate.model.constraint.Constrained;

/**
 * A Object is a data model component which is composed of zero or more
 * {@link CompositeComponentModel composite} components.
 *
 * An Object support multiple inheritance, inheriting composite components from
 * its parent(s).
 *
 * @param <T> associated java data type
 */
public interface ObjectModel<T> extends ModelComponent, Constrained {

    /**
     * Domain object model(s) this object inherits from, potentially inheriting
     * components.
     *
     * @return domain component from which this component is inherited or, if
     * not inherited from other object models, returns an empty set
     */
    Set<ObjectModel<? super T>> getParents();

    /**
     * {@link ComposableModelComponent Components} from which this object model
     * is comprised.
     *
     * @return collection of composite components defined natively, through
     * composites, or inheritance
     */
    Collection<? extends ComposableModelComponent<?>> getComposites();
}
