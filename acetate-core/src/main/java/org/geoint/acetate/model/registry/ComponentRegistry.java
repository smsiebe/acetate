package org.geoint.acetate.model.registry;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 * Registry of component models within a domain model.
 */
public interface ComponentRegistry {

    /**
     * Returns a component by the requested domain-unique component name.
     *
     * @param componentName case-insensitive component name
     * @return component model, if found
     */
    Optional<ComponentModel<?>> findByName(String componentName);

    /**
     * Returns an immutable collection of domain model components which are
     * decorated with the specified attribute.
     *
     * @param attributeType attribute type
     * @return collection of component models which are decorated with the
     * requested attribute, or an empty collection if not components have the
     * requested attribute
     */
    Collection<ComponentModel<?>> findByAttribute(
            Class<? extends ComponentAttribute> attributeType);

    /**
     * Returns an immutable collection of domain model components which
     * specialize (inherits from) the specified component.
     *
     * @param parentComponentName component name to search for specialized
     * components
     * @return collection of component models which specialize (inherit from)
     * the requested component, or an empty collection
     */
    Collection<ComponentModel<?>> findSpecialized(String parentComponentName);
}
