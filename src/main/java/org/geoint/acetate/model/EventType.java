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
public class EventType extends ComposedType {

    private final ImmutableNamedMap<NamedRef> composites;

    public EventType(TypeDescriptor descriptor,
            Collection<NamedRef> composites)
            throws InvalidModelException {
        this(descriptor, null, composites);
    }

    public EventType(TypeDescriptor descriptor, String description,
            Collection<NamedRef> composites)
            throws InvalidModelException {
        super(descriptor, description);
        this.composites
                = ImmutableNamedMap.createMap(composites, NamedRef::getName);
    }

    public Collection<NamedRef> getComposites() {
        return composites.values();
    }

    public Optional<NamedRef> findComposite(String name) {
        return composites.find(name);
    }

}
