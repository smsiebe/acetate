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
import java.util.Optional;
import org.geoint.acetate.EventInstance;

/**
 * Domain-defined event describing an action domain, normally resulting from the
 * successful completion oIf a {@link ResourceOperation operation}.
 *
 * @see EventInstance
 * @see ResourceOperation
 * @author steve_siebert
 */
public final class EventType extends DomainType {

    private final ImmutableNamedTypeMap<NamedTypeRef<ValueType>> composites;

    public EventType(String namespace, String name, String version,
            Collection<NamedTypeRef<ValueType>> composites)
            throws InvalidModelException {
        this(namespace, name, version, null, composites);
    }

    public EventType(String namespace, String name,
            String version, String description,
            Collection<NamedTypeRef<ValueType>> composites)
            throws InvalidModelException {
        super(namespace, name, version, description);
        this.composites
                = ImmutableNamedTypeMap.createMap(composites, NamedTypeRef::getName);
    }

    public Collection<NamedTypeRef<ValueType>> getComposites() {
        return composites.values();
    }

    public Optional<NamedTypeRef<ValueType>> findComposite(String name) {
        return composites.find(name);
    }

}
