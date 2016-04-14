/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.model;

import org.geoint.acetate.util.ImmutableNamedMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The model of a unique application domain.
 *
 * @author steve_siebert
 */
public final class DomainModel {

    private final String namespace;
    private final String version;
    private final Optional<String> description;
    private final ImmutableNamedMap<ResourceType> resources;
    private final ImmutableNamedMap<EventType> events;
    private final ImmutableNamedMap<ValueType> values;
    private final Set<TypeDescriptor> dependencies;

    public DomainModel(String namespace, String version, String description,
            Collection<ResourceType> resources,
            Collection<EventType> events,
            Collection<ValueType> values,
            Collection<TypeDescriptor> dependencies) throws InvalidModelException {
        this.namespace = namespace;
        this.version = version;
        this.description = Optional.ofNullable(description);
        this.resources = ImmutableNamedMap.createMap(resources, ResourceType::getName);
        this.events = ImmutableNamedMap.createMap(events,
                EventType::getName, this.resources::containsKey);
        this.values = ImmutableNamedMap.createMap(values,
                ValueType::getName,
                (n) -> this.resources.containsKey(n) || this.events.containsKey(n));
        this.dependencies = new HashSet<>(dependencies);
    }

    public static DomainModel newModel(String namespace, String version,
            Collection<DomainType> types) throws InvalidModelException {
        return newModel(namespace, version, null, types);
    }

    public static DomainModel newModel(String namespace, String version,
            String description, Collection<DomainType> types)
            throws InvalidModelException {
        Collection<ResourceType> resources = new ArrayList<>();
        Collection<EventType> events = new ArrayList<>();
        Collection<ValueType> values = new ArrayList<>();
        Set<TypeDescriptor> dependencies = new HashSet<>();

        for (DomainType t : types) {
            if (!t.getNamespace().contentEquals(namespace)
                    || !t.getVersion().contentEquals(version)) {
                dependencies.add(TypeDescriptor.valueOf(t));
            } else if (t instanceof ResourceType) {
                resources.add((ResourceType) t);
            } else if (t instanceof EventType) {
                events.add((EventType) t);
            } else if (t instanceof ValueType) {
                values.add((ValueType) t);
            } else {
                throw new InvalidModelException(String.format("Unknown "
                        + "domain type '%s'", t.toString()));
            }
        }
        return new DomainModel(namespace, version, description,
                resources, events, values, dependencies);
    }

    /**
     * Namespace of the domain.
     *
     * @return domain namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Domain version.
     *
     * @return domain version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Optional domain description.
     *
     * @return optional domain description
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Models of domains this model depends upon.
     *
     * @return domain dependencies
     */
    public Set<TypeDescriptor> getDependencies() {
        return this.dependencies;
    }

    /**
     * Resources native to this domain.
     *
     * @return domain resource models
     */
    public Collection<ResourceType> getResources() {
        return resources.values();
    }

    public ResourceType getResource(String typeName)
            throws UnknownTypeException {
        return resources.getOrThrow(typeName,
                () -> new UnknownTypeException(namespace, version, typeName));
    }

    /**
     * Values native to this domain.
     *
     * @return domain value models
     */
    public Collection<ValueType> getValues() {
        return values.values();
    }

    public ValueType getValue(String typeName) throws UnknownTypeException {
        return values.getOrThrow(typeName,
                () -> new UnknownTypeException(namespace, version, typeName));
    }

    /**
     * Events native to this domain.
     *
     * @return domain event models
     */
    public Collection<EventType> getEvents() {
        return events.values();
    }

    public EventType getEvent(String typeName) throws UnknownTypeException {
        return events.getOrThrow(typeName,
                () -> new UnknownTypeException(namespace, version, typeName));
    }

    /**
     * Returns a Stream of all the domain types.
     *
     * @return domain type stream
     */
    public Stream<DomainType> typeStream() {
        return Stream.concat(
                Stream.concat(resources.values().stream(),
                        events.values().stream()
                ), values.values().stream());
    }

    @Override
    public String toString() {
        return String.format("%s-%s", namespace, version);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.namespace);
        hash = 11 * hash + Objects.hashCode(this.version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DomainModel other = (DomainModel) obj;
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        return Objects.equals(this.version, other.version);
    }

}
