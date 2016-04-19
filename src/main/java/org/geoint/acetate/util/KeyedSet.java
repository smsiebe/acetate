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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.geoint.acetate.functional.ThrowingFunction;

/**
 * A Set which associates a key to an item when it is added.
 * <p>
 * This Set is very similar to a Map, except it requires a unique,
 * deterministic, key to be generated the the same way for every item by
 * permitting the key to be registered only once. As a result, both the key and
 * value are unique to the set.
 * <p>
 * Instances of a keyed set is not thread safe.
 *
 * @author steve_siebert
 * @param <K> key type
 * @param <V> item type
 */
public class KeyedSet<K, V> implements Set<V> {

    private final Map<K, V> items = new HashMap<>();
    private final Function<V, K> keyGen;

    /**
     *
     * @param keyGenerator function used to generate the item key
     */
    public KeyedSet(Function<V, K> keyGenerator) {
        this.keyGen = keyGenerator;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.values().contains(o);
    }

    @Override
    public Iterator<V> iterator() {
        return items.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return items.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return items.values().toArray(a);
    }

    /**
     * Re-key each item and return a list.
     *
     * @param <T>
     * @param <X>
     * @param mapper
     * @return
     * @throws X
     */
    public <T, X extends Throwable> List<T> rekeyList(
            ThrowingFunction<V, T, X> mapper) throws X {
        return rekeyCollection(ArrayList::new, mapper);
    }

    /**
     * Rekey each item and return a new collection created by the provided
     * supplier.
     *
     * @param <T>
     * @param <C>
     * @param <X>
     * @param collectionSupplier
     * @param mapper
     * @return
     * @throws X
     */
    public <T, C extends Collection<T>, X extends Throwable> C rekeyCollection(
            Supplier<C> collectionSupplier,
            ThrowingFunction<V, T, X> mapper) throws X {
        C to = collectionSupplier.get();
        for (V v : this.items.values()) {
            to.add(mapper.apply(v));
        }
        return to;
    }

    /**
     * Rekey, returning a new set.
     *
     * @param <T>
     * @param <X>
     * @param mapper
     * @return
     * @throws X
     */
    public <T, X extends Throwable> Set<T> rekeySet(
            ThrowingFunction<V, T, X> mapper) throws X {
        return rekeyCollection(HashSet::new, mapper);
    }

    /**
     * Returns an unmodifiable map of a key->value map backed by this set.
     *
     * @return
     */
    public Map<K, V> toMap() {
        return Collections.unmodifiableMap(items);
    }

    public BidirectionalMap<K, V> toBidirectionalMap() {
        BidirectionalMap biMap = BidirectionalMap.newMap(() -> new HashMap<>());
        biMap.putAll(items);
        return biMap;
    }

    /**
     * Returns a new keyed set containing only items which match the type
     * filters.
     *
     * @param <T>
     * @param valueType
     * @return
     */
    public <T extends V> KeyedSet<K, T> typedFilter(Class<T> valueType) {
        KeyedSet typedSet = new KeyedSet<>(keyGen);
        this.items.entrySet().stream()
                .filter((e) -> valueType.equals(e.getValue().getClass()))
                .forEach((e) -> typedSet.items.put(e.getKey(), e.getValue()));
        return typedSet;
    }

    /**
     * Create a new, immutable, keyed set by copying the content of this keyed
     * set.
     * <p>
     * Changes to this set will not change any created immutable set.
     *
     * @return immutable keyed set
     */
    public ImmutableKeyedSet<K, V> toImmutableSet() {
        return ImmutableKeyedSet.createSet(this);
    }

    public Optional<V> findByKey(K name) {
        return Optional.ofNullable(items.get(name));
    }

    /**
     * Attempts to add the named ref to the set.
     *
     * @param item item
     * @return true if item was unique, otherwise false
     */
    @Override
    public boolean add(V item) {
        final K key = keyGen.apply(item);
        if (items.containsKey(key)) {
            return false;
        }
        items.put(key, item);
        return true;
    }

    public V addIfAbsent(K key, Supplier<V> valueSupplier) {
        if (!items.containsKey(key)) {
            items.put(key, valueSupplier.get());
        }
        return items.get(key);
    }

    public void addOrThrow(V item) throws DuplicatedKeyedItemException {
        if (!add(item)) {
            throw new DuplicatedKeyedItemException(String.format("Item '%s' "
                    + "could not be added, generates a duplicate key",
                    item.toString()));
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.values().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        for (V v : c) {
            boolean res = this.add(v);
            if (!res) {
                return res;
            }
        }
        return true;
    }

    public void addAllOrThrow(Collection<? extends V> c)
            throws DuplicatedKeyedItemException {
        final Map<K, V> newItems = new HashMap<>();
        for (V v : c) {
            final K k = keyGen.apply(v);
            if (items.containsKey(k)) {
                throw new DuplicatedKeyedItemException(String.format("Item '%s' "
                        + "generates a duplicate key '%s', no items were "
                        + "added to the keyed set.", v.toString(), k.toString()));
            }
            newItems.put(k, v);
        }
        //all items a unique, add them all
        this.items.putAll(newItems);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        final K key = keyGen.apply((V) o);
        return this.items.remove(key, o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        items.clear();
    }

}
