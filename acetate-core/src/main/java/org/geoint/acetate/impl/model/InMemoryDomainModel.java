package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.geoint.acetate.model.DomainModel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.DomainRegistry;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;

/**
 * Domain model maintained in-memory.
 */
public class InMemoryDomainModel implements DomainModel {

    private final String domainId;
    private final String name;
    private final long version;
    private final Map<ComponentAddress, ModelComponent> components;
    private final Map<Class<? extends ComponentAttribute>, Collection<Attributable>> attributedComponents;
    private final Map<ComponentAddress, Collection<ObjectModel<?>>> specializedComponents;

    private static final Logger logger
            = Logger.getLogger(InMemoryDomainModel.class.getName());

    /**
     *
     * An domain model constructed directly by application code will not be
     * registered with a {@link DomainRegistry}, nor will it have been validated
     * against other domain model instances. It's highly recommended for
     * applications to register the domain model with a registry by asking the
     * registry to scan for components.
     *
     * @param name domain model name
     * @param version domain model version
     * @throws ModelException thrown if the model is invalid
     */
    public InMemoryDomainModel(String name,
            long version)
            throws ModelException {
        this.name = name;
        this.version = version;
        this.domainId = DomainUtil.uniqueDomainId(name, version);

        //various component indexes
        this.components = new HashMap<>();
        this.attributedComponents = new HashMap<>();
        this.specializedComponents = new HashMap<>();
    }

    public void add(ModelComponent component)
            throws ModelException, IncompleteModelException, ComponentCollisionException {

        if (!component.getAddress().getDomainName().contentEquals(name)) {
            throw new ModelException(name, version,
                    component.getAddress().asString()
                    + " not added to  domain "
                    + this.toString()
                    + "; component does not match domain name.");
        }

        if (component.getAddress().getDomainVersion() != version) {
            throw new ModelException(name, version,
                    component.getAddress().asString()
                    + " not added to  domain "
                    + this.toString()
                    + "; component does not match domain version.");
        }

        //add to attribute index
        component.getAttributes().stream()
                .map((a) -> a.getClass())
                .distinct()
                .forEach((a) -> addMatching(this.attributedComponents, a, component));

        //add to specialized index
        if (component instanceof ObjectModel) {
            ObjectModel<?> obj = (ObjectModel<?>) component;
            obj.getParents()
                    .forEach((a) -> addMatching(this.specializedComponents, a, obj));
        }

        //add to address index
        this.components.put(component.getAddress(), component);

    }

    @Override
    public String getDomainId() {
        return domainId;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<ModelComponent> findAll() {
        return components.values();
    }

    @Override
    public Optional<ModelComponent> find(ComponentAddress address) {
        return Optional.ofNullable(components.get(address));
    }

    @Override
    public Collection<Attributable> find(
            Class<? extends ComponentAttribute> attributeType) {
        return findMatches(attributedComponents, attributeType);
    }

    @Override
    public Collection<ObjectModel<?>> findSpecialized(
            ComponentAddress parentAddress) {
        return findMatches(specializedComponents, parentAddress);
    }

    @Override
    public String toString() {
        return domainId;
    }

    /*
     * Searches the map for the key, returning the resulting collection or 
     * an empty collection.
     * 
     */
    private <K, V> Collection<V> findMatches(
            Map<K, Collection<V>> map, K query) {
        Collection<V> results = map.get(query);
        return (results == null) ? Collections.EMPTY_LIST : results;
    }

    /*
     * Add matching item to the map accepting multiple values for each key 
     *(multi-map), creating the map entry as needed.
     */
    private static <K, V> void addMatching(Map<K, Collection<V>> map, K key, V source) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(source);
    }

}
