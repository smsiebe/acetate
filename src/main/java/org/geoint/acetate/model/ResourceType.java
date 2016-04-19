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

import org.geoint.acetate.util.ImmutableKeyedSet;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.util.DuplicatedKeyedItemException;

/**
 * Model of a {@link DomainResource domain resource}, which is a specialized
 * domain type that is a version controlled type that can be uniquely identified
 * by a unique identifier.
 *
 * @see DomainResource
 * @author steve_siebert
 */
public class ResourceType extends ComposedType {

    //special index of just the composite resources
    private final ImmutableKeyedSet<String, NamedTypeRef<ResourceType>> links;
    private final ImmutableKeyedSet<String, ResourceOperation> operations;

    public ResourceType(TypeDescriptor descriptor,
            Collection<NamedRef> composites,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {
        this(descriptor, null, composites, operations);
    }

    public ResourceType(TypeDescriptor descriptor,
            String description,
            Collection<NamedRef> composites,
            Collection<ResourceOperation> operations)
            throws InvalidModelException {

        super(descriptor, composites, description);

        try {
            this.operations = ImmutableKeyedSet.createSet(operations,
                    ResourceOperation::getName);
        } catch (DuplicatedKeyedItemException ex) {
            throw new InvalidModelException("Resource operation names must be "
                    + "unique.", ex);
        }

        for (ResourceOperation o : this.operations) {
            //operation names must not conflict with composite reference names
            if (this.composites.containsKey(o.getName())) {
                throw new InvalidModelException(String.format("Resource '%s' "
                        + "contains an operation and resource named '%s', "
                        + "operation names must be unique to both operations "
                        + "and resource reference names.",
                        descriptor.toString(),
                        o.getName()));
            }
        }

        try {
            //create a convenience map of links (composites which are resources)
            this.links = ImmutableKeyedSet.createSet(composites.stream()
                    .filter((r) -> r instanceof NamedTypeRef)
                    .map((r) -> (NamedTypeRef) r)
                    .filter((r) -> r.getReferencedType() instanceof ResourceType)
                    .map((r) -> (NamedTypeRef<ResourceType>) r)
                    .collect(Collectors.toList()),
                    NamedTypeRef::getName);
        } catch (DuplicatedKeyedItemException ex) {
            //we shouldn't get here, since the composites are already vetted
            throw new InvalidModelException("Links must have unique "
                    + "reference names.");
        }
    }

    /**
     * Operations defined by the resource.
     *
     * @return resource operations
     */
    public Collection<ResourceOperation> getOperations() {
        return operations;
    }

    /**
     * Return the resource operation by name, or null.
     *
     * @param operationName operation name
     * @return operation or null
     */
    public Optional<ResourceOperation> findOperation(String operationName) {
        return operations.findByKey(operationName);
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
        return links;
    }

    /**
     * Return the resource link details for the associated link name, or null if
     * no link exists by this name for this resource.
     *
     * @param linkName link name
     * @return link details or null if link by this name does not exist
     */
    public Optional<NamedTypeRef<ResourceType>> findLink(String linkName) {
        return links.findByKey(linkName);
    }

}
