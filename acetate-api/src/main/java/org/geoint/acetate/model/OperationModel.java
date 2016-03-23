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

import java.util.List;
import java.util.Optional;

/**
 * A behavioral operation of a {@link ResourceModel resource}.
 * <p>
 * Only a OperationModel may expose behavior of a domain, the results of which
 * is encapsulated in the returned {@link EventModel event}.
 *
 * @author steve_siebert
 */
//@Domain(namespace = "acetate.model", type = "operation", version = "1.0")
public interface OperationModel<E extends EventModel> {
    
    /**
     * Namespace of the declaring resource.
     * 
     * @return resource namespace
     */
    String getResourceNamespace();
    
    /**
     * Domain type of the declaring resource.
     * 
     * @return resource type
     */
    String getResourceType();
    
    /**
     * Version of the declaring resource.
     * 
     * @return resource version
     */
    String getResourceVersion();
    
    /**
     * The resource operation name.
     * <p>
     * Together, the resource namespace, resource type, resource version, and
     * operation name uniquely identifies a specific resource operation.
     *
     * @return resource action which may be restricted
     */
    String getName();

    /**
     * Operation description, which may be null.
     *
     * @return resource description
     */
    Optional<String> getDescription();

    /**
     * Indicates if this operation is idempotent.
     *
     * @return true if this operation is idempotent, otherwise false
     */
    boolean isIdempotent();

    /**
     * Indicates if this operation is safe.
     *
     * @return true if this operation is safe, otherwise false
     */
    boolean isSafe();

    /**
     * Models describing the parameters of the operation.
     * <p>
     * The parameter order of the returned array mirrors that of the underlying
     * java method.
     *
     * @return operation parameters, if none returns an empty array
     */
    List<? extends NamedTypeRef> getParameters();

    /**
     * Model describing the resource returned from the operation.
     * <p>
     * All domain operations MUST return a resource, otherwise it is not a valid
     * domain {@link OperationModel}.
     *
     * @return operation response model
     */
    NamedTypeRef<E> getSuccessEventType();

}
