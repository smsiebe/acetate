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

import java.util.Optional;
import java.util.Set;

/**
 * Model of a resource which composes a domain.
 *
 *
 * @author steve_siebert
 */
//@Domain(namespace="acetate.model", type="resource", version="1.0")
public interface ResourceModel extends TypeModel {

    /**
     * Operations defined by the resource.
     *
     * @return resource operations
     */
    Set<? extends OperationModel> getOperations()
            throws InvalidModelException;

    /**
     * Return the resource operation by name, or null.
     *
     * @param operationName operation name
     * @return operation or null
     */
    Optional<? extends OperationModel> findOperation(String operationName)
            throws InvalidModelException;

    /**
     * Resource data attribute models.
     *
     * @return resource data attribute models
     */
    Set<? extends NamedTypeRef<? extends ValueModel>> getAttributes()
            throws InvalidModelException;

    /**
     * Return the attribute associated with the name, or null.
     *
     * @param name attribute name
     * @return the attribute information or null if the attribute name is not
     * valid for this resource
     */
    Optional<? extends NamedTypeRef<? extends ValueModel>> findAttribute(String name)
            throws InvalidModelException;

    /**
     * Resources linked to this domain resource.
     *
     * @return resources linked to this resource
     */
    Set<? extends NamedTypeRef<? extends ResourceModel>> getLinks()
            throws InvalidModelException;

    /**
     * Return the resource link details for the associated link name, or null if
     * no link exists by this name for this resource.
     *
     * @param linkName link name
     * @return link details or null if link by this name does not exist
     */
    Optional<? extends NamedTypeRef<? extends ResourceModel>> findLink(String linkName)
            throws InvalidModelException;

}
