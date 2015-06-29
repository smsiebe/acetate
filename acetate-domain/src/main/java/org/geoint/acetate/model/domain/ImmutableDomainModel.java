package org.geoint.acetate.model.domain;

import org.geoint.acetate.model.ImmutableModelComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.geoint.acetate.DomainRegistry;
import org.geoint.acetate.impl.model.IncorrectModelAssignementException;
import org.geoint.acetate.impl.meta.model.DomainId;
import org.geoint.acetate.meta.model.DomainModel;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.meta.model.ModelException;
import org.geoint.acetate.model.attribute.Attributed;
import org.geoint.acetate.model.attribute.ModelAttribute;

/**
 * Immutable domain model instance.
 */
public final class ImmutableDomainModel implements DomainModel {

    private final DomainId id;
    private final Map<String, ModelComponent> components;
    private final Map<Class<? extends ModelAttribute>, Collection<Attributed>> attributedComponents;

    /**
     *
     * An domain model constructed directly by application code will not be
     * registered with a {@link DomainRegistry}, nor will it have been validated
     * against other domain model instances. It's highly recommended for
     * applications to register the domain model with a registry by asking the
     * registry to scan for components.
     *
     * @param id domain identity
     * @param components components of the domain
     * @throws ModelException thrown if the model is invalid
     */
    public ImmutableDomainModel(DomainId id,
            Collection<ImmutableModelComponent> components)
            throws ModelException {
        this.id = id;

        //various component indexes
        this.components = new HashMap<>();
        this.attributedComponents = new HashMap<>();
//        this.specializedComponents = new HashMap<>();

        for (ModelComponent component : components) {
            if (!component.getDomainId().getName().contentEquals(id.getName())) {
                throw new IncorrectModelAssignementException(id.getName(),
                        id.getVersion(),
                        component.getName(), component.toString()
                        + " was not added to  domain "
                        + this.toString()
                        + "; component does not match domain name.");
            }

            if (component.getDomainId().getVersion() != id.getVersion()) {
                throw new IncorrectModelAssignementException(id.getName(),
                        id.getVersion(),
                        component.toString()
                        + " not added to  domain "
                        + this.toString()
                        + "; component does not match domain version.");
            }

            //add to attribute index
            component.getAttributes().stream()
                    .map((a) -> a.getClass())
                    .distinct()
                    .forEach((a) -> addMatching(this.attributedComponents, a, component));

            //add to address index
            this.components.put(component.getName(), component);
        }

    }

    @Override
    public String getId() {
        return id.asString();
    }

    @Override
    public long getVersion() {
        return id.getVersion();
    }

    @Override
    public String getName() {
        return id.getName();
    }

    @Override
    public Collection<ModelComponent> findAll() {
        return components.values();
    }

    @Override
    public Optional<ModelComponent> find(String componentName) {
        return Optional.ofNullable(components.get(componentName));
    }

    @Override
    public Collection<Attributed> find(
            Class<? extends ModelAttribute> attributeType) {
        return findMatches(attributedComponents, attributeType);
    }

    @Override
    public String toString() {
        return "domain " + id.asString();
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
