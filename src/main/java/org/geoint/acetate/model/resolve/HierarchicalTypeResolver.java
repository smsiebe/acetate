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
package org.geoint.acetate.model.resolve;

import java.util.Optional;
import org.geoint.acetate.model.DomainType;

/**
 * A type resolver that "fails up", checking a parent type resolver if it cannot
 * locally resolveType a type.
 * <p>
 * Each HierarchicalTypeResolver may have one parent and the resolution of a
 * domain type is up the hierarchy until a type is found.
 *
 * @author steve_siebert
 * @param <T> domain type key
 */
public final class HierarchicalTypeResolver<T> implements DomainTypeResolver<T> {

    private final HierarchicalTypeResolver<T> parent; //null indicates the root/parent node
    private final DomainTypeResolver<T> resolver;

    private HierarchicalTypeResolver(DomainTypeResolver<T> resolver) {
        this(null, resolver);
    }

    private HierarchicalTypeResolver(HierarchicalTypeResolver<T> parent,
            DomainTypeResolver<T> resolver) throws NullPointerException {
        if (resolver == null) {
            throw new NullPointerException("HierarchicalTypeResolver node must "
                    + "have a local type resolver.");
        }
        this.parent = parent;
        this.resolver = resolver;
    }

    public static <T> HierarchicalTypeResolver<T> newHierarchy(DomainTypeResolver<T> resolver) {
        return new HierarchicalTypeResolver(resolver);
    }

    /**
     * Creates a new HierarchicalTypeResolver with this resolver as the parent
     * and the provided resolver as its local resolver.
     *
     * @param resolver local resolver
     * @return new hierarchical resolver
     * @throws NullPointerException if the local resolver is null
     */
    public HierarchicalTypeResolver<T> addChild(DomainTypeResolver<T> resolver) {
        return new HierarchicalTypeResolver(this, resolver);
    }

    /**
     * Creates a new HierarchicalTypeResolver if the provided local resolver is
     * not null, otherwise returns itself.
     *
     * @param resolver local resolver
     * @return new child resolver or the same resolver if provided resolver was
     * null
     */
    public HierarchicalTypeResolver<T> optionalChild(DomainTypeResolver<T> resolver) {
        return (resolver == null)
                ? this
                : addChild(resolver);
    }

    @Override
    public DomainType resolve(T key) throws UnresolvedException {

        //recursively check hierarchy
        try {
            return this.resolver.resolve(key);
        } catch (UnresolvedException ex) {
            if (parent == null) {
                throw ex;
            }
            return parent.resolve(key);
        }
    }

}
