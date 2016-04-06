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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.EventInstance;
import org.geoint.acetate.InstanceRef;

/**
 * Domain-defined event describing an action domain, normally resulting from the
 * successful completion oIf a {@link ResourceOperation operation}.
 *
 * @see EventInstance
 * @see ResourceOperation
 * @author steve_siebert
 */
public final class EventType extends DomainType {

    private final ImmutableNamedMap<NamedRef> composites;

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
                = ImmutableNamedMap.createMap(composites, NamedRef::getName);
    }

    public Collection<NamedRef> getComposites() {
        return composites.values();
    }

    public Optional<NamedRef> findComposite(String name) {
        return composites.find(name);
    }

    public EventInstance createInstance(Collection<InstanceRef> composites)
            throws InvalidModelException {
        return new DefaultEventInstance(this, composites);
    }

    private static class DefaultEventInstance implements EventInstance {

        private final EventType model;
        private final Map<String, InstanceRef> composites;

        public DefaultEventInstance(EventType model,
                Collection<InstanceRef> composites)
                throws InvalidModelException {
            this.model = model;

            Map<String, InstanceRef> compositeIndex = new HashMap<>();
            for (InstanceRef c : composites) {
                if (!model.findComposite(c.getName()).isPresent()) {
                    throw new InvalidModelException(String.format(
                            "Invalid reference '%s' for composite on event "
                            + "type '%s'", c.getName(), model.toString()));
                }
                compositeIndex.put(c.getName(), c);
            }
            this.composites = Collections.unmodifiableMap(compositeIndex);
        }

        @Override
        public Collection<InstanceRef> getComposites() {
            return this.composites.values();
        }

        @Override
        public Optional<InstanceRef> findComposite(String compositeName) {
            return Optional.ofNullable(composites.get(compositeName));
        }

        @Override
        public EventType getModel() {
            return this.model;
        }

        @Override
        public String toString() {
            return String.format("Instance of event type '%s'", model.toString());
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 71 * hash + Objects.hashCode(this.model);
            hash = 71 * hash + Objects.hashCode(this.composites);
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
            final DefaultEventInstance other = (DefaultEventInstance) obj;
            if (!Objects.equals(this.model, other.model)) {
                return false;
            }
            return Objects.equals(this.composites, other.composites);
        }

    }
}
