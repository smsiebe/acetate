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

import org.geoint.acetate.model.design.DomainModelBuilder;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.geoint.acetate.model.resolve.MapTypeResolver;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.model.resolve.HierarchicalTypeResolver;

/**
 * Domain model registry.
 * <p>
 * DomainRegistry instances are <b>NOT</b> thread-safe, applications must
 * implement external locking if this is required.
 *
 * @author steve_siebert
 */
public class DomainRegistry implements DomainTypeResolver<TypeDescriptor> {

    //local registry - registered new types here
    protected final MapTypeResolver<TypeDescriptor> localRegistry;
    //complete registry - resolve types from here
    protected final HierarchicalTypeResolver<TypeDescriptor> typeResolver;

    protected DomainRegistry() {
        this.localRegistry = new MapTypeResolver<>(new ConcurrentHashMap<>());
        this.typeResolver = HierarchicalTypeResolver.newHierarchy(localRegistry);
    }

    protected DomainRegistry(DomainTypeResolver<TypeDescriptor> resolver) {
        this.localRegistry = new MapTypeResolver<>();
        this.typeResolver = HierarchicalTypeResolver.newHierarchy(resolver)
                .addChild(localRegistry);
    }

    /**
     * Create a new DomainRegistry backed by just its (currently empty)
     * register.
     *
     * @return empty domain registry
     */
    public static DomainRegistry newRegistry() {
        return new DomainRegistry();
    }

    /**
     * Create a new DomainRegistry that attempts to resolve domain types from
     * its local register and falls back to the provided type resolver if not
     * found.
     *
     * @param resolver type resolver searched if not found in local register
     * @return new registry
     */
    public static DomainRegistry newRegistry(
            DomainTypeResolver<TypeDescriptor> resolver) {
        return new DomainRegistry(resolver);
    }

    /**
     * Create a new domain builder which registers the created domain types on
     * successful build.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @return domain builder
     */
    public DomainModelBuilder builder(String namespace, String version) {
        return new RegisteringDomainBuilder(new DomainModelBuilder(namespace, version, this));
    }

//    /**
//     * Returns an unmodifiable collection of domain models known to this
//     * registry.
//     *
//     * @return domain models
//     */
//    @Override
//    public Set<DomainModel> getDomainModels() {
//        return Collections.unmodifiableSet(models);
//    }
    public void register(DomainModel model) {
        //explode the model, registering unknown types to the local register
        model.typeStream()
                .filter((t) -> !typeResolver.resolveType(t.getTypeDescriptor()).isPresent())
                .forEach(this::register);
    }

    public void register(DomainType type) {
        localRegistry.getTypes().put(type.getTypeDescriptor(), type);
    }

    public DomainModel findModel(String namespace, String version)
            throws InvalidModelException {
        //create a new domain model from the exploded types
        return DomainModel.newModel(namespace, version,
                localRegistry.getTypes().values());
    }

    @Override
    public Optional<DomainType> resolveType(TypeDescriptor td) {
        return typeResolver.resolveType(td);
    }

    /**
     * Decorates a DomainModelBuilder, registering the created types on build.
     */
    private class RegisteringDomainBuilder extends DomainModelBuilder {

        private final DomainModelBuilder builder;

        public RegisteringDomainBuilder(DomainModelBuilder builder) {
            super(builder.getNamespace(), builder.getVersion());
            this.builder = builder;
        }

        @Override
        public DomainModel build() throws InvalidModelException {
            DomainModel model = builder.build();
            register(model);
            return model;
        }

        @Override
        public String getNamespace() {
            return builder.getNamespace();
        }

        @Override
        public String getVersion() {
            return builder.getVersion();
        }

        @Override
        public DomainModelBuilder withDescription(String domainModelDescription) throws IllegalStateException {
            return builder.withDescription(domainModelDescription);
        }

        @Override
        public ValueBuilder defineValue(String typeName) throws InvalidModelException, IllegalStateException {
            return builder.defineValue(typeName);
        }

        @Override
        public ValueBuilder defineValue(String typeName, String desc) throws InvalidModelException {
            return builder.defineValue(typeName, desc);
        }

        @Override
        public EventBuilder defineEvent(String typeName) throws InvalidModelException {
            return builder.defineEvent(typeName);
        }

        @Override
        public EventBuilder defineEvent(String typeName, String desc) throws InvalidModelException {
            return builder.defineEvent(typeName, desc);
        }

        @Override
        public ResourceBuilder defineResource(String typeName) throws InvalidModelException {
            return builder.defineResource(typeName);
        }

        @Override
        public ResourceBuilder defineResource(String typeName, String desc) throws InvalidModelException {
            return builder.defineResource(typeName, desc);
        }

        @Override
        public int hashCode() {
            return builder.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return builder.equals(obj);
        }

        @Override
        public String toString() {
            return builder.toString();
        }

    }
}
