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

import java.util.HashMap;
import org.geoint.acetate.util.BidirectionalMap;

/**
 * Bidirectional resolver backed by a bidirectional map.
 *
 * @author steve_siebert
 */
public class BidirectionalMapResolver<K, V> implements BidirectionalResolver<K, V> {

    private final BidirectionalMap<K, V> biMap;

    public BidirectionalMapResolver() {
        this.biMap = BidirectionalMap.newMap(() -> new HashMap<>());
    }

    public BidirectionalMapResolver(BidirectionalMap<K, V> biMap) {
        this.biMap = biMap;
    }

    @Override
    public K reverse(V value) throws UnresolvedException {
        return biMap.getKeyOrThrow(value,
                () -> new UnresolvedException(String.format("No registered "
                        + "key for value '%s'", value.toString())));
    }

    @Override
    public V resolve(K key) throws UnresolvedException {
        return biMap.getValueOrThrow(key,
                () -> new UnresolvedException(String.format("No registered "
                        + "value for key '%s'", key.toString())));
    }

    public BidirectionalMap<K, V> getMap() {
        return this.biMap;
    }
}
