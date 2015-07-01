package org.geoint.acetate.entity.attic.attribute;

import java.util.ArrayList;
import java.util.Collection;
import org.geoint.acetate.meta.annotation.Model;

/**
 * An Attributed model component can be associated with one or more
 * {@link ModelAttribute attributes}.
 *
 * A model component attribute provides a point of extensibility to model
 * processing frameworks, allowing frameworks to create, and associate, their
 * own {@link ModelAttribute attributes} to components.
 *
 * Base attributes may be defined domain model components and overwritten though
 * inheritance. Attributes on a component are not affected by
 * composition/aggregation.
 *
 * @see ModelAttribute
 *
 */
@Model(name="", domainName="acetate", domainVersion=1)
public interface Attributed {

    /**
     * Returns all the component attributes.
     *
     * @return attributes of the component
     */
    Collection<? extends ModelAttribute> getAttributes();

    /**
     * Returns the attribute(s) for the requested ModelAttribute type.
     *
     * @param <C> component attribute type
     * @param type attribute type
     * @return collection of attributes matching the provided type or an empty
     * collection
     */
    default <C extends ModelAttribute> Collection<C> getAttributes(
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
            Class<? extends ModelAttribute> attributeType) {
        return getAttributes().stream()
                .anyMatch((a) -> a.getClass().equals(attributeType));
    }
}
