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
import java.util.stream.Collectors;
import org.geoint.acetate.InstanceRef;
import org.geoint.acetate.ResourceInstance;
import org.geoint.acetate.TypeInstanceRef;

/**
 * Model of a {@link DomainResource domain resource}, which is a specialized
 * domain type that is a version controlled type that can be uniquely identified
 * by a unique identifier.
 *
 * @see DomainResource
 * @author steve_siebert
 */
public class ResourceType extends ComposedType {

    private final ImmutableNamedMap<NamedRef> composites;
    //special index of just the composite resources
    private final ImmutableNamedMap<NamedTypeRef<ResourceType>> links;
    private final ImmutableNamedMap<ResourceOperation> operations;

    public ResourceType(String namespace, String version, String name,
            Collection<NamedRef> composites,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        this(namespace, version, name, null, composites, operations);
    }

    public ResourceType(String namespace, String version, String name,
            String description,
            Collection<NamedRef> composites,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        super(namespace, version, name, description);
        this.composites = ImmutableNamedMap.createMap(composites,
                NamedRef::getName);
        //operation names must not conflict with composite reference names
        this.operations = ImmutableNamedMap.createMap(operations,
                ResourceOperation::getName, this.composites::containsKey);

        //create a map of composites that are themselves 
        //resource types, so we don't need to generate a new collection 
        //on each request at runtime
        this.links = ImmutableNamedMap.createMap(composites.stream()
                .filter((r) -> r instanceof NamedTypeRef)
                .map((r) -> (NamedTypeRef) r)
                .filter((r) -> r.getReferencedType() instanceof ResourceType)
                .map((r) -> (NamedTypeRef<ResourceType>) r)
                .collect(Collectors.toList()),
                NamedTypeRef::getName);
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
     * Composite type models.
     * <p>
     * Note this method returns all composite types, including links.
     *
     * @return resource data attribute models
     */
    public Collection<NamedRef> getComposites() {
        return composites.values();
    }

    /**
     * Return the composite type associated with the name, or null.
     *
     * @param name attribute name
     * @return the attribute information or null if the attribute name is not
     * valid for this resource
     */
    public Optional<NamedRef> findComposite(String name) {
        return composites.find(name);
    }

    /**
     * Return just the composite types that are themselves resources.
     * <p>
     * A composite relationship between two resources is considered a "link",
     * allowing the resources to be managed independently (ie version
     * controlled) but maintain their relationship.
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
