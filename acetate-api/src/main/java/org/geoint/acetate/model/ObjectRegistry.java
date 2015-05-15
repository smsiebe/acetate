package org.geoint.acetate.model;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 * Registry of component models within a domain model.
 */
public interface ObjectRegistry {

    /**
     * Returns an immutable collection containing all the model components
     * within the domain model.
     *
     * @return all components within the domain model
     */
    Collection<ObjectModel<?>> findAll();

    /**
     * Returns a component by the requested domain-unique component name.
     *
     * @param componentName case-insensitive component name
     * @return component model, if found
     */
    Optional<ObjectModel<?>> findByName(String componentName);

    /**
     * Returns an immutable collection of domain model components which are
     * decorated with the specified attribute.
     *
     * @param attributeType attribute type
     * @return collection of component models which are decorated with the
     * requested attribute, or an empty collection if not components have the
     * requested attribute
     */
    Collection<ObjectModel<?>> findByAttribute(
            Class<? extends ComponentAttribute> attributeType);

    /**
     * Returns an immutable collection of domain model objects which specialize
     * (inherits from) the specified objects.
     *
     * @param parentObjectName component name to search for specialized
     * components
     * @return collection of object models which specialize (inherit from) the
     * requested object, or an empty collection
     */
    Collection<ObjectModel<?>> findSpecialized(String parentObjectName);
}
