package gov.ic.geoint.acetate;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.address.ComponentAddress;
import org.geoint.acetate.model.DomainComponent;
import org.geoint.acetate.model.DomainObject;
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
    Collection<DomainObject<?>> findAll();

    /**
     * Returns a component model by its unique domain address.
     *
     * @param address domain model component address
     * @return component model or null if the address does not resolve to a
     * component
     */
    Optional<DomainComponent<?>> findByAddress(ComponentAddress address);

    /**
     * Returns an immutable collection of domain model components which are
     * decorated with the specified attribute.
     *
     * @param attributeType attribute type
     * @return collection of component models which are decorated with the
     * requested attribute, or an empty collection if not components have the
     * requested attribute
     */
    Collection<DomainObject<?>> findByAttribute(
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
    Collection<DomainObject<?>> findSpecialized(String parentObjectName);
}
