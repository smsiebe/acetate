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

/**
 * Model of a {@link DomainResource}.
 *
 * @see DomainResource
 * @author steve_siebert
 */
public final class ResourceType extends DomainType {

    private final ImmutableNamedTypeMap<NamedTypeRef<ValueType>> composites;
    private final ImmutableNamedTypeMap<NamedTypeRef<ResourceType>> links;
    private final ImmutableNamedTypeMap<ResourceOperation> operations;

    public ResourceType(String namespace, String name, String version,
            Collection<NamedTypeRef<ValueType>> composites,
            Collection<NamedTypeRef<ResourceType>> links,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        this(namespace, name, version, null, composites, links, operations);
    }

    public ResourceType(String namespace, String name,
            String version, String description,
            Collection<NamedTypeRef<ValueType>> composites,
            Collection<NamedTypeRef<ResourceType>> links,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        super(namespace, name, version, description);
        this.composites = ImmutableNamedTypeMap.createMap(composites,
                NamedTypeRef::getName);
        this.links = ImmutableNamedTypeMap.createMap(links,
                NamedTypeRef::getName, 
                this.composites::containsKey);
        this.operations = ImmutableNamedTypeMap.createMap(operations,
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
    Optional<ResourceOperation> findOperation(String operationName) {
        return operations.find(operationName);
    }

    /**
     * Composite types.
     *
     * @return resource data attribute models
     */
    Collection<NamedTypeRef<ValueType>> getComposites() {
        return composites.values();
    }

    /**
     * Return the attribute associated with the name, or null.
     *
     * @param name attribute name
     * @return the attribute information or null if the attribute name is not
     * valid for this resource
     */
    Optional<NamedTypeRef<ValueType>> findComposite(String name) {
        return composites.find(name);
    }

    /**
     * Resources linked to this domain resource.
     *
     * @return resources linked to this resource
     */
    Collection<NamedTypeRef<ResourceType>> getLinks() {
        return links.values();
    }

    /**
     * Return the resource link details for the associated link name, or null if
     * no link exists by this name for this resource.
     *
     * @param linkName link name
     * @return link details or null if link by this name does not exist
     */
    Optional<NamedTypeRef<ResourceType>> findLink(String linkName) {
        return links.find(linkName);
    }

}
