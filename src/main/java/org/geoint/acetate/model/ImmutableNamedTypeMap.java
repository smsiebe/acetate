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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Simply a map wrapper that provide convenience methods and enforces rules
 * common do domain model references.
 * <p>
 * Rules:
 * <ul>
 * <li>Each item must have a valid name. If the resolved item name is null or an
 * empty String an InvalidModelException is thrown.
 * <li>There may only be one item with the same name. If another item is named
 * the same a DuplicateNamedTypeException is thrown.</li>
 * </ul>
 *
 * @author steve_siebert
 * @param <V> map value
 */
public final class ImmutableNamedTypeMap<V> implements Map<String, V> {

    private final Map<String, V> map;
    private static final Supplier<Map> DEFAULT_MAP_SUPPLIER = () -> new HashMap<>();

    ImmutableNamedTypeMap(Map<String, V> map) {
        this.map = map;
    }

    public static <V> ImmutableNamedTypeMap<V> createMap(Collection<V> items,
            Function<V, String> namer) throws InvalidModelException {
        return createMap(DEFAULT_MAP_SUPPLIER, items, namer, null);
    }

    public static <V> ImmutableNamedTypeMap<V> createMap(
            Collection<V> items,
            Function<V, String> namer,
            Predicate<String> duplicateNameFilter)
            throws InvalidModelException {
        return createMap(DEFAULT_MAP_SUPPLIER, items, namer, duplicateNameFilter);
    }

    public static <V> ImmutableNamedTypeMap<V> createMap(
            Supplier<Map> mapSupplier,
            Collection<V> items,
            Function<V, String> namer) throws InvalidModelException {
        return createMap(mapSupplier, items, namer, null);
    }

    public static <V> ImmutableNamedTypeMap<V> createMap(
            Supplier<Map> mapSupplier,
            Collection<V> items,
            Function<V, String> namer,
            Predicate<String> duplicateNameFilter)
            throws InvalidModelException {
        Map<String, V> map = mapSupplier.get();
        for (V i : items) {
            final String name = namer.apply(i);
            if (name == null || name.isEmpty()) {
                throw new InvalidModelException("Named map item must have a "
                        + "non-null/non-empty name.");
            }

            //check for name collision
            if ((duplicateNameFilter != null)
                    ? duplicateNameFilter.and(map::containsKey).test(name)
                    : map.containsKey(name)) {
                throw new DuplicateNamedTypeException(name);
            }
            map.put(name, i);
        }
        return new ImmutableNamedTypeMap(map);
    }

    public Optional<V> find(String name) {
        return Optional.ofNullable(map.get(name));
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }

    @Override
    public V put(String key, V value) {
        throw new UnsupportedOperationException("Immutable map, not supported.");
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("Immutable map, not supported.");
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        throw new UnsupportedOperationException("Immutable map, not supported.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Immutable map, not supported.");
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ImmutableNamedTypeMap)) {
            return false;
        }
        return map.equals(((ImmutableNamedTypeMap) other).map);
    }

}
