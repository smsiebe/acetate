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

import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.DuplicateNamedTypeException;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.ResourceOperation;
import org.geoint.acetate.model.ResourceType;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.util.KeyedSet;

/**
 *
 * @author steve_siebert
 */
public class ResourceBuilder extends ComposedTypeBuilder<ResourceType, ResourceBuilder> {

    final KeyedSet<String, ModelBuilder<ResourceOperation>> operations
            = new KeyedSet<>(ModelBuilder::getName);

    public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
            TypeDescriptor typeDescriptor) {
        super(onBuild, typeDescriptor);
    }
//
//        public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
//                TypeDescriptor typeDescriptor, String description) {
//            super(onBuild, typeDescriptor, description);
//        }

    public OperationBuilder withOperation(String opName)
            throws InvalidModelException {
        OperationBuilder op = new OperationBuilder(this, opName);
        addOperation(op);
        return op;
    }

    public ResourceBuilder withOperation(ResourceOperation operation)
            throws InvalidModelException {
        addOperation(new BuiltOperationBuilder(operation));
        return this;
    }

    protected void addOperation(ModelBuilder<ResourceOperation> opBuilder)
            throws InvalidModelException {
        if (!operations.add(opBuilder)) {
            throw new DuplicateNamedTypeException(opBuilder.getName());
        }
    }

//        /**
//         * Defines local (same domain) linked resource type.
//         *
//         * @param refName local name for this linked resource
//         * @param localTypeName domain type name of the resource
//         * @throws InvalidModelException if invalid definition
//         * @return this builder (fluid interface)
//         */
//        public NamedTypeRefBuilder<ResourceBuilder> withLink(String refName,
//                String localTypeName)
//                throws InvalidModelException {
//            return withLink(refName, defaultNamespace, defaultVersion,
//                    localTypeName);
//        }
    /**
     * Defines a ResourceType (same or external domain) as a resource link.
     *
     * @param refName local name for this linked resource
     * @param compositeNamespace defaultNamespace of the link type
     * @param compositeVersion verison of the link type
     * @param compositeName type name of the link type
     * @throws InvalidModelException if invalid definition
     * @return this builder (fluid interface)
     */
    public NamedTypeRefBuilder<ResourceBuilder> withLink(String refName,
            String compositeNamespace, String compositeVersion,
            String compositeName) throws InvalidModelException {

        NamedTypeRefBuilder<ResourceBuilder> ref = new NamedTypeRefBuilder(this, refName,
                compositeNamespace, compositeVersion, compositeName);
        addCompositeRef(ref);
        return ref;
    }

    /**
     * Defines a link with previously resolved reference model.
     *
     * @param ref reference model
     * @return this builder
     * @throws InvalidModelException if invalid definition
     */
    public ResourceBuilder withLink(NamedTypeRef ref)
            throws InvalidModelException {
        NamedTypeRefBuilder<ResourceBuilder> refBuilder
                = new NamedTypeRefBuilder(this, ref);
        addCompositeRef(refBuilder);
        return this;
    }

    @Override
    public ResourceType createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {

        return new ResourceType(typeDescriptor, description,
                getCompositeRefs(resolver),
                operations.rekeyList((o) -> o.createModel(resolver)));
    }
}
