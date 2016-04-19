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

import org.geoint.acetate.model.DomainType;

/**
 *
 * @author steve_siebert
 * @param <T>
 */
public class HierarchicalBiTypeResolver<T> implements BidirectionalResolver<T, DomainType> {

    private HierarchicalBiTypeResolver<T> parent;
    private BidirectionalResolver<T, DomainType> resolver;

    public HierarchicalBiTypeResolver(BidirectionalResolver<T, DomainType> resolver) {
        this(null, resolver);
    }

    public HierarchicalBiTypeResolver(HierarchicalBiTypeResolver<T> parent,
            BidirectionalResolver<T, DomainType> resolver) throws NullPointerException {
        if (resolver == null) {
            throw new NullPointerException("HierarchicalBiTypeResolver node must "
                    + "have a local type resolver.");
        }
        this.parent = parent;
        this.resolver = resolver;
    }

    public static <T> HierarchicalBiTypeResolver<T> newHierarchy(BidirectionalResolver<T, DomainType> resolver) {
        return new HierarchicalBiTypeResolver(resolver);
    }

    public HierarchicalBiTypeResolver<T> addChild(BidirectionalResolver<T, DomainType> resolver) {
        return new HierarchicalBiTypeResolver(this, resolver);
    }

    public HierarchicalBiTypeResolver<T> optionalChild(BidirectionalResolver<T, DomainType> resolver) {
        return (resolver == null)
                ? this
                : addChild(resolver);
    }

    @Override
    public T reverse(DomainType td) throws UnresolvedException {
        //recursively check hierarchy
        try {
            return this.resolver.reverse(td);
        } catch (UnresolvedException ex) {
            if (parent == null) {
                throw ex;
            }
            return parent.reverse(td);
        }
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
