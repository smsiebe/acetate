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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.geoint.acetate.model.DomainType;

/**
 * Map-backed type resolver.
 * <p>
 * The thread safety of this class depends on the underlying collection used.
 *
 * @param <K>
 */
public class MapTypeResolver<K> implements DomainTypeResolver<K> {

    private final Map<K, DomainType> types;

    /**
     * Creates a non-thread-safe type in-memory resolver.
     */
    public MapTypeResolver() {
        types = new HashMap<>();
    }

    /**
     * Creates a fixed-sized in-memory resolver backed by the provided array.
     *
     * @param keyGenerator generates the keys for each domain type
     * @param types types resolved by the resolver
     */
    public MapTypeResolver(Function<DomainType, K> keyGenerator,
            DomainType... types) {
        this(Arrays.stream(types)
                .collect(Collectors.toMap(keyGenerator::apply, (t) -> t))
        );
    }

    /**
     * Uses the provided map as the source for the resolver.
     * <p>
     * Consider thread-safety here.
     *
     * @param types
     */
    public MapTypeResolver(Map<K, DomainType> types) {
        this.types = types;
    }

    /**
     * Returns the type map backing this resolver.
     *
     * @return resolved types
     */
    public Map<K, DomainType> getTypes() {
        return types;
    }

    @Override
    public Optional<DomainType> resolveType(K key) {
        return Optional.ofNullable(types.get(key));
    }

}
