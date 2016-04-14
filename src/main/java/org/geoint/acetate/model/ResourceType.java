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
import java.util.stream.Collectors;

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

}
