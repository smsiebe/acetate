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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Simple in-memory type resolver.
 * <p>
 * The thread safety of this class depends on the underlying collection used.
 */
public class MemoryTypeResolver implements TypeResolver {

    private final Collection<DomainType> types;

    /**
     * Creates a non-thread-safe type in-memory resolver.
     */
    public MemoryTypeResolver() {
        types = new ArrayList<>();
    }

    /**
     * Creates a fixed-sized in-memory resolver backed by the provided array.
     *
     * @param types types resolved by the resolver
     */
    public MemoryTypeResolver(DomainType... types) {
        this.types = Arrays.asList(types);
    }

    /**
     * Uses the provided collection as the source for the domain types.
     * <p>
     * If you plan on using this resolver across threads you should use a
     * concurrent collection here.
     *
     * @param types
     */
    public MemoryTypeResolver(Collection<DomainType> types) {
        this.types = types;
    }

    /**
     * Returns the types known to the resolver instance.
     * <p>
     * This method returns the actual collection used by the resolver, so any
     * changes made to this collection will impact the resolver. Consider thread
     * safety here.
     *
     * @return resolved types
     */
    public Collection<DomainType> getTypes() {
        return types;
    }

    @Override
    public Optional<DomainType> resolve(String namespace, String version,
            String typeName) {
        return types.stream()
                .filter((t) -> t.isType(namespace, version, typeName))
                .findFirst();
    }

}
