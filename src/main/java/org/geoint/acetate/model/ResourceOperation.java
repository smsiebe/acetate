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
import org.geoint.acetate.ResourceInstance;

/**
 * Behavior of a domain is described as resource operations, returning events
 * describing the results of the behavior.
 *
 * @see ResourceType
 * @see ResourceInstance
 * @see EventType
 * @see EventInstace
 * @author steve_siebert
 */
public final class ResourceOperation {

    private final String namespace;
    private final String version;
    private final String resourceName;
    private final String operationName;
    private final Optional<String> description;
    private final boolean idempotent;
    private final boolean safe;
    private final ImmutableNamedTypeMap<NamedTypeRef> parameters;
    private final NamedTypeRef<EventType> returnEvent;

    public ResourceOperation(String namespace, String version,
            String resourceName, String operationName, boolean idempotent,
            boolean safe,
            Collection<NamedTypeRef> parameters,
            NamedTypeRef<EventType> returnEvent) throws InvalidModelException {
        this(namespace, version, resourceName, operationName, null,
                idempotent, safe, parameters, returnEvent);
    }

    public ResourceOperation(String namespace, String version,
            String resourceName, String operationName, String description,
            boolean idempotent, boolean safe,
            Collection<NamedTypeRef> parameters,
            NamedTypeRef<EventType> returnEvent) throws InvalidModelException {
        this.namespace = namespace;
        this.version = version;
        this.resourceName = resourceName;
        this.operationName = operationName;
        this.description = Optional.ofNullable(description);
        this.idempotent = idempotent;
        this.safe = safe;
        this.parameters
                = ImmutableNamedTypeMap.createMap(parameters, NamedTypeRef::getName);
        this.returnEvent = returnEvent;
    }

    /**
     * Namespace of the declaring resource.
     *
     * @return resource namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Version of the declaring resource.
     *
     * @return resource version
     */
    public String getResourceVersion() {
        return version;
    }

    /**
     * Domain type of the declaring resource.
     *
     * @return resource type
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * The resource operation name.
     * <p>
     * Together, the resource namespace, resource type, resource version, and
     * operation name uniquely identifies a specific resource operation.
     *
     * @return resource action which may be restricted
     */
    public String getName() {
        return operationName;
    }

    /**
     * Operation description, which may be null.
     *
     * @return resource description
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Indicates if this operation is idempotent.
     *
     * @return true if this operation is idempotent, otherwise false
     */
    public boolean isIdempotent() {
        return idempotent;
    }

    /**
     * Indicates if this operation is safe.
     *
     * @return true if this operation is safe, otherwise false
     */
    public boolean isSafe() {
        return safe;
    }

    /**
     * Models describing the parameters of the operation.
     * <p>
     * The parameter order of the returned array mirrors that of the underlying
     * java method.
     *
     * @return operation parameters, if none returns an empty array
     */
    public Collection<NamedTypeRef> getParameters() {
        return parameters.values();
    }

    /**
     * Model describing the resource returned from the operation.
     * <p>
     * All operations <b>MUST</b> return a single (no collection, no map) domain
     * event, otherwise it is not a valid domain {@link ResourceOperation}.
     *
     * @return operation response model
     */
    public NamedTypeRef<EventType> getSuccessEventType() {
        return returnEvent;
    }

}
