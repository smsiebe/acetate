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
package org.geoint.acetate;

import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.ResourceModel;
import org.geoint.acetate.model.ValueModel;

/**
 * An instance of a domain resource.
 *
 * @param <M> model used to describe the domain resource
 */
public interface ResourceInstance<M extends ResourceModel> extends TypeInstance<M> {

    @Override
    public M getModel();

    /**
     * Resource instance globally unique identifier.
     *
     * @return immutable unique identifier for the resource
     */
    String getResourceGuid();

    /**
     * The instance version identifier.
     *
     * @return resource version
     */
    String getResourceVersion();

    /**
     * The previous version of this resource.
     *
     * @return previous version resource or null if this the resource did not
     * have a previous version
     */
    Optional<String> getPreviousResourceVersion();

    /**
     * Attributes set on the resource.
     *
     * @return resource attributes
     */
    Set<? extends NamedTypeRef<? extends ValueModel>> getAttributes();

    /**
     * Return an attribute by name.
     *
     * @param attributeName attribute name
     * @return object describing the attribute reference or null
     */
    Optional<? extends NamedTypeRef<? extends ValueModel>> findAttribute(String attributeName);

    Set<? extends NamedTypeRef<? extends ResourceModel>> getLinks();

    Optional<? extends NamedTypeRef<? extends ResourceModel>> findLink(String linkName);

    Set<? extends OperationInstance> getOperations();

    Optional<? extends OperationInstance> findOperation(String operationName);
}
