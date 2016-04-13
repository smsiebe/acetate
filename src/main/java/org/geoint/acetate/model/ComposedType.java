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

import java.util.Objects;
import java.util.Optional;

/**
 * A type that is composed from other domain types.
 * <p>
 * A composite domain type may simply be a container of other domain types or be
 * a special domain type, such as a {@link ResourceType resource} or
 * {@link EventType event} which has special significance to the domain model.
 *
 * @author steve_siebert
 */
public abstract class ComposedType implements DomainType {

    protected final TypeDescriptor descriptor;
    private final Optional<String> description;

    public ComposedType(String namespace, String version, String name) {
        this(namespace, version, name, null);
    }

    public ComposedType(String namespace, String version, String name,
            String description) {
        this.descriptor = new TypeDescriptor(namespace, version, name);
        this.description = Optional.ofNullable(description);
    }

    @Override
    public TypeDescriptor getTypeDescriptor() {
        return descriptor;
    }

    /**
     * Optional description for the domain type.
     *
     * @return description
     */
    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return descriptor.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.descriptor);
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
        final DomainType other = (DomainType) obj;
        return Objects.equals(this.descriptor, other.getTypeDescriptor());
    }
}
