package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Optional;
import org.geoint.acetate.model.DomainModel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 * Domain model maintained in-memory.
 */
public class InMemoryDomainModel implements DomainModel {

    private final String domainId;
    private final String name;
    private final long version;
    private final String displayName;
    private final Optional<String> description;
    private final Map<ComponentAddress, ModelComponent> components;
    private final Map<Class<? extends ComponentAttribute>, Collection<Attributable>> attributedComponents;
    private final Map<ComponentAddress, Collection<ObjectModel<?>>> specializedComponents;

    private static final Logger logger
            = Logger.getLogger(InMemoryDomainModel.class.getName());

    /**
     * Create an in-memory domain model.
     *
     * An domain model constructed by application code will not be registered
     * with a {@link DomainRegistry}, nor will it have been validated against
     * other domain model instances. It's highly recommended for applications to
     * register the domain model with a registry by asking the registry to scan
     * for components.
     *
     * @param name domain model name
     * @param version domain model version
     * @param displayName optional display name of model...if null will be
     * generated
     * @param description optional description of the domain model
     * @param components components that make up this model
     * @throws ModelException thrown if the model is invalid
     */
    public InMemoryDomainModel(String name,
            long version,
            String displayName,
            String description,
            Collection<ModelComponent> components)
            throws ModelException {
        this.name = name;
        this.version = version;
        this.domainId = DomainUtil.uniqueDomainId(name, version);
        this.displayName = displayName;
        this.description = Optional.ofNullable(description);

        //filter model components and to indexes
        this.components = new HashMap<>();
        this.attributedComponents = new HashMap<>();
        this.specializedComponents = new HashMap<>();

        components.stream()
                .filter(this::filterDomainName)
                .filter(this::filterDomainVersion)
                .map((c) -> {
                    //add to attribute map
                    c.getAttributes().stream()
                    .map((a) -> a.getClass())
                    .distinct()
                    .forEach((a) -> addMatching(this.attributedComponents, a, c));
                    return c;
                })
                .map((c) -> {
                    //add to specialization map
                    if (c instanceof ObjectModel) {
                        ObjectModel<?> obj = (ObjectModel<?>) c;
                        addMatchingRecursive(this.specializedComponents,
                                ObjectModel::getAddress,
                                obj,
                                ObjectModel::getParents);
                    }
                    return c;
                })
                .forEach((c) -> {
                    //add to address map
                    this.components.put(c.getAddress(), c);
                });

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
    public String getDisplayName() {
        return (displayName != null) ? displayName : toString();
    }

    @Override
    public Optional<String> getDescription() {
        return description;
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
        return displayName;
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

    private boolean filterDomainName(ModelComponent c) {
        if (!c.getAddress().getDomainName().contentEquals(name)) {
            logger.log(Level.FINEST, "{0}"
                    + " not added to "
                    + "domain {1}" + "; component does not match domain "
                    + "name.",
                    new Object[]{c.getAddress().asString(), this.toString()});
            return false;
        }
        return true;
    }

    private boolean filterDomainVersion(ModelComponent c) {
        if (c.getAddress().getDomainVersion() != version) {
            logger.log(Level.FINEST, "{0}"
                    + " not added to "
                    + "domain {1}" + "; component does not match domain "
                    + "version.",
                    new Object[]{c.getAddress().asString(), this.toString()});
            return false;
        }
        return true;
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

    /*
     * Recursively adds matching item(s) to the map
     */
    private static <K, V> void addMatchingRecursive(Map<K, Collection<V>> map,
            Function<V, K> keyFunc,
            V source,
            Function<V, Collection<V>> recursiveFunc) {
        addMatching(map, keyFunc.apply(source), source);
        //recursive
        recursiveFunc.apply(source).stream()
                .forEach((r) -> addMatchingRecursive(map, keyFunc, r, recursiveFunc));
    }
}
