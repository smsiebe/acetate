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
package org.geoint.acetate.java;

import org.geoint.acetate.java.model.ResourceClass;
import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.ResourceInstance;

/**
 * Java object representing a resource instance.
 * <p>
 * A domain resource instance must be able to be uniquely identified by a single
 * GUID and instance version.
 *
 * @author steve_siebert
 * @param <T> java class representation of a domain resource
 */
public interface ResourceObject<T>
        extends TypeObject<T, ResourceClass<T>>, ResourceInstance<ResourceClass<T>> {

    @Override
    public ResourceClass<T> getModel();

    /**
     * Data attributes, as objects, of this resource.
     *
     * @return resource attribute objects
     */
    @Override
    public Set<ValueObjectRef<?>> getAttributes();

    @Override
    public Optional<ValueObjectRef<?>> findAttribute(String attributeName);

    /**
     * Linked resources, as objects.
     *
     * @return linked resources
     */
    @Override
    Set<ResourceObjectRef<?>> getLinks();

    @Override
    public Optional<ResourceObjectRef<?>> findLink(String linkName);

    @Override
    public Optional<OperationMethod<T, ?>> findOperation(String operationName);

    @Override
    public Set<OperationMethod<T, ?>> getOperations();

}
