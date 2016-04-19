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
package org.geoint.acetate.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*
 *
 * TODO consider refactoring and moving this to geoint-commons
 */
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
 * <p>
 * Instances of ImmutableKeyedSet are thread safe.
 *
 *
 * @author steve_siebert
 * @param <K>
 * @param <V> map value
 */
public final class ImmutableKeyedSet<K, V> implements Set<V> {

    private final Map<K, V> map;

    private ImmutableKeyedSet(Map<K, V> map) {
        this.map = map;
    }

    private ImmutableKeyedSet(Supplier<Map<K, V>> mapSupplier,
            Collection<V> items,
            Function<V, K> keyGen) throws DuplicatedKeyedItemException {

        this.map = mapSupplier.get();

        for (V i : items) {

            if (map.containsValue(i)) {
                continue; //skip item, already added to set
            }

            final K key = keyGen.apply(i);
            if (map.containsKey(key)) {
                throw new DuplicatedKeyedItemException(String.format("Key '%s' "
                        + "already exists in keyed set, set was not created.",
                        key.toString()));
            }

            map.put(key, i);
        }
    }

    public static <K, V> ImmutableKeyedSet<K, V> createSet(KeyedSet<K, V> set) {
        return new ImmutableKeyedSet(set.toMap());
    }

    public static <K, V> ImmutableKeyedSet<K, V> createSet(Collection<V> items,
            Function<V, K> keyGen) throws DuplicatedKeyedItemException {
        return new ImmutableKeyedSet(() -> new HashMap<>(), items, keyGen);
    }

    public static <K, V> ImmutableKeyedSet<K, V> createSet(
            Supplier<Map<K, V>> mapSupplier,
            Collection<V> items,
            Function<K, V> keyGen) throws DuplicatedKeyedItemException {
        return new ImmutableKeyedSet(mapSupplier, items, keyGen);
    }

    public Optional<V> findByKey(K name) {
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

    public V getOrDefault(Object key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public <X extends Throwable> V getOrThrow(K key, Supplier<X> thrower)
            throws X {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw thrower.get();
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public boolean contains(Object o) {
        return this.map.containsValue(o);
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Iterator<V> iterator() {
        //TODO verify the remove method doesn't work
        return this.map.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.map.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.map.values().toArray(a);
    }

    @Override
    public boolean add(V e) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Immutable set, not supported.");
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ImmutableKeyedSet)) {
            return false;
        }
        return map.equals(((ImmutableKeyedSet) other).map);
    }

}
