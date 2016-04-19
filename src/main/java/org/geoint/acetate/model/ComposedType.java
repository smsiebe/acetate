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

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.util.DuplicatedKeyedItemException;
import org.geoint.acetate.util.ImmutableKeyedSet;

/**
 * A type that is composed from other domain types.
 * <p>
 * A composite domain type may simply be a container of other domain types or be
 * a special domain type, such as a {@link ResourceType resource} or
 * {@link EventType event} which has special significance to the domain model.
 *
 * @author steve_siebert
 */
public class ComposedType implements DomainType {

    protected final TypeDescriptor descriptor;
    protected final Optional<String> description;
    protected final ImmutableKeyedSet<String, NamedRef> composites;

    public ComposedType(TypeDescriptor descriptor,
            Collection<NamedRef> composites)
            throws InvalidModelException {
        this(descriptor, composites, null);
    }

    public ComposedType(TypeDescriptor descriptor,
            Collection<NamedRef> composites,
            String description) throws InvalidModelException {
        this.descriptor = descriptor;
        try {
            this.composites
                    = ImmutableKeyedSet.createSet(composites, NamedRef::getName);
        } catch (DuplicatedKeyedItemException ex) {
            throw new InvalidModelException("Duplicate composite type name.", ex);
        }
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

    public Collection<NamedRef> getComposites() {
        return composites;
    }

    public Optional<NamedRef> findComposite(String name) {
        return composites.findByKey(name);
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
