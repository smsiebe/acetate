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
import java.util.HashMap;
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
 *
 * @author steve_siebert
 * @param <K> key type
 * @param <V> item type
 */
public class KeyedSet<K, V> implements Set<V> {

    private final Map<String, V> items = new HashMap<>();
    private final Function<V, String> keyGen;

    /**
     *
     * @param keyGenerator function used to generate the item key
     */
    public KeyedSet(Function<V, String> keyGenerator) {
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
     * Maps each item in the set to a new List.
     * <p>
     *
     * @param <T>
     * @param <X>
     * @param mapper
     * @return
     * @throws X
     */
    public <T, X extends Throwable> List<T> toList(
            ThrowingFunction<V, T, X> mapper) throws X {
        return toCollection(ArrayList::new, mapper);
    }

    public <T, C extends Collection<T>, X extends Throwable> C toCollection(
            Supplier<C> collectionSupplier,
            ThrowingFunction<V, T, X> mapper) throws X {
        C to = collectionSupplier.get();
        for (V v : this.items.values()) {
            to.add(mapper.apply(v));
        }
        return to;
    }

    public Optional<V> findByName(String name) {
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
        final String name = keyGen.apply(item);
        if (items.containsKey(name)) {
            return false;
        }
        items.put(name, item);
        return true;
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

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        final String key = keyGen.apply((V) o);
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
