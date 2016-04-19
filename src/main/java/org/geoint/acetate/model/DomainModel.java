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

import org.geoint.acetate.util.ImmutableKeyedSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.geoint.acetate.util.KeyedSet;

/**
 * The model of a unique application domain.
 *
 * @author steve_siebert
 */
public final class DomainModel {

    private final String namespace;
    private final String version;
    private final Optional<String> description;
    private final ImmutableKeyedSet<String, ResourceType> resources;
    private final ImmutableKeyedSet<String, EventType> events;
    private final ImmutableKeyedSet<String, ValueType> values;
    private final Set<TypeDescriptor> dependencies;

    public DomainModel(String namespace, String version, String description,
            KeyedSet<String, DomainType> types,
            Collection<TypeDescriptor> dependencies) throws InvalidModelException {
        this.namespace = namespace;
        this.version = version;
        this.description = Optional.ofNullable(description);
        this.resources = types.typedFilter(ResourceType.class).toImmutableSet();
        this.events = types.typedFilter(EventType.class).toImmutableSet();
        this.values = types.typedFilter(ValueType.class).toImmutableSet();
        this.dependencies = new HashSet<>(dependencies);
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
        return resources;
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
        return values;
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
        return events;
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
                Stream.concat(resources.stream(),
                        events.stream()
                ), values.stream());
    }

    /**
     * Returns the formatted domain name as domainName-domainVersion.
     *
     * @return formatted domain name
     */
    public String asString() {
        return String.format("%s-%s", namespace, version);
    }

    @Override
    public String toString() {
        return asString();
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
