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

import java.util.Set;
import org.geoint.acetate.model.design.DomainBuilder;
import java.util.concurrent.ConcurrentHashMap;
import org.geoint.acetate.model.resolve.MapTypeResolver;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.model.resolve.HierarchicalTypeResolver;
import org.geoint.acetate.model.resolve.UnresolvedException;

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

    /**
     * Create a new DomainRegistry backed by just its (currently empty)
     * register.
     *
     */
    public DomainRegistry() {
        this.localRegistry = new MapTypeResolver<>(new ConcurrentHashMap<>());
        this.typeResolver = HierarchicalTypeResolver.newHierarchy(localRegistry);
    }

    /**
     * Create a new DomainRegistry that attempts to resolve domain types from
     * its local register and falls back to the provided type resolver if not
     * found.
     *
     * @param resolver type resolver searched if not found in local register
     */
    public DomainRegistry(DomainTypeResolver<TypeDescriptor> resolver) {
        this.localRegistry = new MapTypeResolver<>();
        this.typeResolver = HierarchicalTypeResolver.newHierarchy(resolver)
                .addChild(localRegistry);
    }

    /**
     * Create a new domain builder which registers the created domain types on
     * successful build.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @return domain builder
     */
    public DomainBuilder builder(String namespace, String version) {
        return new RegisteringDomainBuilder(namespace, version);
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
//    public DomainModel findModel(String namespace, String version)
//            throws InvalidModelException {
//        //create a new domain model from the exploded types
//        DomainModelBuilder mb = new DomainModelBuilder(namespace, version);
//        
//    }
    public void register(DomainModel model) {
        //explode the model, registering unknown types to the local register
        model.typeStream()
                .filter((t) -> typeResolver.canResolve(t.getTypeDescriptor()))
                .forEach(this::register);
    }

    public void register(DomainType type) {
        localRegistry.getTypes().put(type.getTypeDescriptor(), type);
    }

    @Override
    public DomainType resolve(TypeDescriptor key) throws UnresolvedException {
        return typeResolver.resolve(key);
    }

    /**
     * Decorates a DomainBuilder, registering the created types on build.
     */
    private class RegisteringDomainBuilder extends DomainBuilder {

        public RegisteringDomainBuilder(String defaultNamespace, String defaultVersion) {
            super(defaultNamespace, defaultVersion);
        }

        @Override
        public Set<DomainType> createTypes() throws InvalidModelException {
            Set<DomainType> types = super.createTypes(typeResolver);
            types.forEach((t) -> localRegistry.getTypes().put(t.getTypeDescriptor(), t));
            return types;
        }

        @Override
        public Set<DomainType> createTypes(DomainTypeResolver<TypeDescriptor> resolver) throws InvalidModelException {
            Set<DomainType> types = super.createTypes(typeResolver.addChild(resolver));
            types.forEach((t) -> localRegistry.getTypes().put(t.getTypeDescriptor(), t));
            return types;
        }

    }
}
