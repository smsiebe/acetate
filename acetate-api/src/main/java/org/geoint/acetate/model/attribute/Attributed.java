package org.geoint.acetate.model.attribute;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An Attributed model component can be associated with one or more
 {@link ComponentAttribute attributes}.
 *
 * A model component attribute provides a point of extensibility to model
 * processing frameworks, allowing frameworks to create, and associate, their
 * own {@link ComponentAttribute attributes} to components.
 *
 * Base attributes may be defined domain model components and overwritten though
 * inheritance. Attributes on a component are not affected by
 * composition/aggregation.
 *
 * @see ComponentAttribute
 *
 */
public interface Attributed {

    /**
     * Returns all the component attributes.
     *
     * @return attributes of the component
     */
    Collection<? extends ComponentAttribute> getAttributes();

    default <C extends ComponentAttribute> Collection<C> getAttributes(
            Class<C> type) {
        Collection<C> results = new ArrayList<>();
        getAttributes().stream()
                .filter((ca) -> (ca.getClass().equals(type)))
                .forEach((ca) -> {
                    results.add((C) ca);
                });
        return results;
    }

    default boolean hasAttribute(
            Class<? extends ComponentAttribute> attributeType) {
        return getAttributes().stream()
                .anyMatch((a) -> a.getClass().equals(attributeType));
    }
}
