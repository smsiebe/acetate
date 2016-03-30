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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
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
    private final ImmutableNamedTypeMap<ResourceType> resources;
    private final ImmutableNamedTypeMap<EventType> events;
    private final ImmutableNamedTypeMap<ValueType> values;

    public DomainModel(String namespace, String version, String description,
            Collection<ResourceType> resources,
            Collection<EventType> events,
            Collection<ValueType> values) throws InvalidModelException {
        this.namespace = namespace;
        this.version = version;
        this.description = Optional.ofNullable(description);
        this.resources = ImmutableNamedTypeMap.createMap(resources, ResourceType::getName);
        this.events = ImmutableNamedTypeMap.createMap(events,
                EventType::getName, this.resources::containsKey);
        this.values = ImmutableNamedTypeMap.createMap(values,
                ValueType::getName,
                (n) -> this.resources.containsKey(n) || this.events.containsKey(n));
    }

    public static DomainModel newInstance(String namespace, String version,
            Collection<DomainType> types) throws InvalidModelException {
        return newInstance(namespace, version, null, types);
    }

    public static DomainModel newInstance(String namespace, String version,
            String description, Collection<DomainType> types)
            throws InvalidModelException {
        Collection<ResourceType> resources = new ArrayList<>();
        Collection<EventType> events = new ArrayList<>();
        Collection<ValueType> values = new ArrayList<>();

        for (DomainType t : types) {
            if (t instanceof ResourceType) {
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
                resources, events, values);
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

//    /**
//     * Models of domains this model depends upon.
//     *
//     * @return domain dependencies
//     */
//    public Set<DomainModel> getModelDependencies();
    /**
     * Resources native to this domain.
     *
     * @return domain resource models
     */
    public Collection<ResourceType> getResources() {
        return resources.values();
    }

    /**
     * Values native to this domain.
     *
     * @return domain value models
     */
    public Collection<ValueType> getValues() {
        return values.values();
    }

    /**
     * Events native to this domain.
     *
     * @return domain event models
     */
    public Collection<EventType> getEvents() {
        return events.values();
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
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }

}
