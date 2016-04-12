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

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ResourceOperation;
import org.geoint.acetate.model.ResourceType;

/**
 * An instance of a domain resource.
 *
 */
public interface ResourceInstance extends TypeInstance<ResourceType> {

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
     * Composites set on the resource.
     *
     * @return resource composites
     */
    Collection<InstanceRef> getComposites();

    /**
     * Return an composite type by name.
     *
     * @param compositeName composite name
     * @return object describing the composite reference or null
     */
    Optional<InstanceRef> findComposite(String compositeName);

    Collection<TypeInstanceRef> getLinks();

    Optional<TypeInstanceRef> findLink(String linkName);

    default Collection<ResourceOperation> listOperations() {
        return getModel().getOperations();
    }

    Optional<OperationInstance> findOperation(String operationName);

}
