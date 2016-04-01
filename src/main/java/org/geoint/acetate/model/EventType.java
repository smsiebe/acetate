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

    private final ImmutableNamedTypeMap<NamedRef> composites;

    public EventType(String namespace, String version, String name,
            Collection<NamedRef> composites)
            throws InvalidModelException {
        this(namespace, version, name, null, composites);
    }

    public EventType(String namespace,
            String version, String name, String description,
            Collection<NamedRef> composites)
            throws InvalidModelException {
        super(namespace, version, name, description);
        this.composites
                = ImmutableNamedTypeMap.createMap(composites, NamedRef::getName);
    }

    public Collection<NamedRef> getComposites() {
        return composites.values();
    }

    public Optional<NamedRef> findComposite(String name) {
        return composites.find(name);
    }

}
