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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.InstanceRef;
import org.geoint.acetate.ResourceInstance;
import org.geoint.acetate.TypeInstanceRef;

/**
 * Model of a {@link DomainResource}.
 *
 * @see DomainResource
 * @author steve_siebert
 */
public class ResourceType extends ComposedType {

    private final ImmutableNamedMap<NamedRef> composites;
    private final ImmutableNamedMap<NamedTypeRef<ResourceType>> links;
    private final ImmutableNamedMap<ResourceOperation> operations;

    public ResourceType(String namespace, String version, String name,
            Collection<NamedRef> composites,
            Collection<NamedTypeRef<ResourceType>> links,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        this(namespace, version, name, null, composites, links, operations);
    }

    public ResourceType(String namespace, String version, String name,
            String description,
            Collection<NamedRef> composites,
            Collection<NamedTypeRef<ResourceType>> links,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        super(namespace, version, name, description);
        this.composites = ImmutableNamedMap.createMap(composites,
                NamedRef::getName);
        this.links = ImmutableNamedMap.createMap(links,
                NamedTypeRef::getName,
                this.composites::containsKey);
        this.operations = ImmutableNamedMap.createMap(operations,
                ResourceOperation::getName,
                (n) -> this.composites.containsKey(n) || this.links.containsKey(n));
    }

    /**
     * Operations defined by the resource.
     *
     * @return resource operations
     */
    public Collection<ResourceOperation> getOperations() {
        return operations.values();
    }

    /**
     * Return the resource operation by name, or null.
     *
     * @param operationName operation name
     * @return operation or null
     */
    public Optional<ResourceOperation> findOperation(String operationName) {
        return operations.find(operationName);
    }

    /**
     * Composite types.
     *
     * @return resource data attribute models
     */
    public Collection<NamedRef> getComposites() {
        return composites.values();
    }

    /**
     * Return the attribute associated with the name, or null.
     *
     * @param name attribute name
     * @return the attribute information or null if the attribute name is not
     * valid for this resource
     */
    public Optional<NamedRef> findComposite(String name) {
        return composites.find(name);
    }

    /**
     * Resources linked to this domain resource.
     *
     * @return resources linked to this resource
     */
    public Collection<NamedTypeRef<ResourceType>> getLinks() {
        return links.values();
    }

    /**
     * Return the resource link details for the associated link name, or null if
     * no link exists by this name for this resource.
     *
     * @param linkName link name
     * @return link details or null if link by this name does not exist
     */
    public Optional<NamedTypeRef<ResourceType>> findLink(String linkName) {
        return links.find(linkName);
    }

    /**
     * Create a new resource instance based on this type definition.
     *
     * @param guid resource instance unique identifier
     * @param version resource instance version
     * @param previousVersion previous version of the resource (may be null)
     * @param composites composite types that compose the resource
     * @param links links to other resources
     * @return resource instance
     * @throws InvalidModelException if the resource instance definition is not
     * consistent with the model
     */
    public ResourceInstance createInstance(String guid, String version,
            String previousVersion,
            Collection<InstanceRef> composites,
            Collection<TypeInstanceRef> links)
            throws InvalidModelException {

        return new DefaultResourceInstance(this, guid, version, previousVersion,
                composites, links);
    }

    private static class DefaultResourceInstance implements ResourceInstance {

        private final ResourceType model;
        private final String guid;
        private final String version;
        private final Optional<String> previousVersion;
        private final Map<String, InstanceRef> composites;
        private final Map<String, TypeInstanceRef> links;

        public DefaultResourceInstance(ResourceType model, String guid,
                String version,
                String previousVersion,
                Collection<InstanceRef> composites,
                Collection<TypeInstanceRef> links)
                throws InvalidModelException {

            if (guid == null || version == null) {
                throw new InvalidModelException("Resource instances require a "
                        + "GUID and instance version.");
            }

            this.model = model;
            this.guid = guid;
            this.version = version;
            this.previousVersion = Optional.ofNullable(previousVersion);

            Map<String, InstanceRef> compositeIndex = new HashMap<>();
            for (InstanceRef c : composites) {
                if (!model.findComposite(c.getName()).isPresent()) {
                    throw new InvalidModelException(String.format(
                            "Invalid reference '%s' for composite on resource "
                            + "type '%s'", c.getName(), model.toString()));
                }
                compositeIndex.put(c.getName(), c);
            }
            this.composites = Collections.unmodifiableMap(compositeIndex);

            Map<String, TypeInstanceRef> linkIndex = new HashMap<>();
            for (TypeInstanceRef l : links) {
                if (!model.findLink(l.getName()).isPresent()) {
                    throw new InvalidModelException(String.format(
                            "Invalid reference '%s' to link on resource type '%s'",
                            l.getName(), model.toString()));
                }
                linkIndex.put(l.getName(), l);
            }
            this.links = Collections.unmodifiableMap(linkIndex);
        }

        @Override
        public String getResourceGuid() {
            return guid;
        }

        @Override
        public String getResourceVersion() {
            return version;
        }

        @Override
        public Optional getPreviousResourceVersion() {
            return previousVersion;
        }

        @Override
        public ResourceType getModel() {
            return this.model;
        }

        @Override
        public Collection<InstanceRef> getComposites() {
            return this.composites.values();
        }

        @Override
        public Optional<InstanceRef> findComposite(String compositeName) {
            return Optional.ofNullable(this.composites.get(compositeName));
        }

        @Override
        public Collection<TypeInstanceRef> getLinks() {
            return this.links.values();
        }

        @Override
        public Optional<TypeInstanceRef> findLink(String linkName) {
            return Optional.ofNullable(this.links.get(linkName));
        }

        @Override
        public Optional findOperation(String operationName) {
            return model.findOperation(operationName)
                    .map((o) -> {
                        try {
                            return o.createInstance(this);
                        } catch (InvalidModelException ex) {
                            //we won't get here since we know the operation 
                            //exists for this model
                            throw new RuntimeException("Unexpected operation "
                                    + "initialization error", ex);
                        }
                    });
        }

        @Override
        public String toString() {
            return String.format("%s: %s-%s",
                    model.toString(),
                    guid, version);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.model);
            hash = 79 * hash + Objects.hashCode(this.guid);
            hash = 79 * hash + Objects.hashCode(this.version);
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
            final DefaultResourceInstance other = (DefaultResourceInstance) obj;
            if (!Objects.equals(this.guid, other.guid)) {
                return false;
            }
            if (!Objects.equals(this.version, other.version)) {
                return false;
            }
            return Objects.equals(this.model, other.model);
        }

    }

}
