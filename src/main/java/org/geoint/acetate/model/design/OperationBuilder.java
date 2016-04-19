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
package org.geoint.acetate.model.design;

import org.geoint.acetate.EventInstance;
import org.geoint.acetate.InstanceRef;
import org.geoint.acetate.ResourceInstance;
import org.geoint.acetate.functional.ThrowingBiFunction;
import org.geoint.acetate.model.DuplicateNamedTypeException;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.ResourceOperation;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.util.KeyedSet;

/**
 *
 * @author steve_siebert
 */
public class OperationBuilder extends ModelBuilder<ResourceOperation> {

    private final ResourceBuilder resource;
    private final String operationName;
    private String description;
    private boolean idempotent = false;
    private boolean safe = false;
    private ThrowingBiFunction<ResourceInstance, InstanceRef[], EventInstance, ?> function;
    private final KeyedSet<String, NamedTypeRefBuilder> parameters = new KeyedSet<>(NamedTypeRefBuilder::getName);
    private NamedTypeRefBuilder returnEvent;

    public OperationBuilder(ResourceBuilder rb, String operationName) {
        this.resource = rb;
        this.operationName = operationName;
    }

    @Override
    public String getNamespace() {
        return resource.getNamespace();
    }

    @Override
    public String getVersion() {
        return resource.getVersion();
    }

    @Override
    public String getName() {
        return operationName;
    }

    public OperationBuilder withDescription(String desc) {
        this.description = (desc == null || desc.isEmpty()) ? null : desc;
        return this;
    }

    public OperationBuilder safe() {
        this.safe = true;
        return this;
    }

    public OperationBuilder notSafe() {
        this.safe = false;
        return this;
    }

    public OperationBuilder idempotent() {
        this.idempotent = true;
        return this;
    }

    public OperationBuilder notIdempotent() {
        this.idempotent = false;
        return this;
    }

    public OperationBuilder withFunction(
            ThrowingBiFunction<ResourceInstance, InstanceRef[], EventInstance, ?> function)
            throws InvalidModelException {
        this.function = function;
        return this;
    }

    public NamedTypeRefBuilder<OperationBuilder> withParameter(
            String paramName, String domainTypeName)
            throws InvalidModelException {
        return withParameter(paramName, getNamespace(), getVersion(), domainTypeName);
    }

    public NamedTypeRefBuilder<OperationBuilder> withParameter(String paramName,
            String paramNamespace, String paramVersion, String paramTypeName)
            throws InvalidModelException {
        NamedTypeRefBuilder<OperationBuilder> ref
                = new NamedTypeRefBuilder(this, paramName,
                        paramNamespace, paramVersion, paramTypeName);

        if (!parameters.add(ref)) {
            throw new DuplicateNamedTypeException(paramName);
        }
        return ref;
    }

//        public NamedTypeRefBuilder<OperationBuilder> createsEvent(
//                String refName, String localDomainTypeName)
//                throws InvalidModelException {
//            return createsEvent(refName, defaultNamespace, defaultVersion, localDomainTypeName);
//        }
    public NamedTypeRefBuilder<OperationBuilder> createsEvent(
            String refName, String eventNamespace, String eventVersion,
            String eventDomainTypeName) throws InvalidModelException {
        this.returnEvent = new NamedTypeRefBuilder(this, refName,
                eventNamespace, eventVersion, eventDomainTypeName);
        return this.returnEvent;
    }

    public ResourceBuilder complete() {
        return resource;
    }

    @Override
    public ResourceOperation createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {
        return new ResourceOperation(resource.typeDescriptor.getNamespace(),
                resource.typeDescriptor.getVersion(),
                this.resource.getName(), operationName, description,
                idempotent, safe, function,
                parameters.rekeyList((r) -> r.createModel(resolver)),
                returnEvent.createModel(resolver));
    }

}
