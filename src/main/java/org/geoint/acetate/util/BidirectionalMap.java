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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.geoint.acetate.functional.ThrowingFunction;

/**
 * Bidirectional map, mapping keys to values and values to keys.
 * <p>
 * Bidirectional maps provide a thread-safe way to create a map which maintains
 * a one-to-one relationship between a key and value.
 *
 * //TODO fully implement {@link Map} interface.
 *
 * @author steve_siebert
 * @param <K> key type
 * @param <V> value type
 */
public abstract class BidirectionalMap<K, V> {

    public abstract int size();

    public abstract boolean isEmpty();

    public abstract boolean containsKey(K key);

    public abstract boolean containsValue(V value);

    public abstract V getValue(K key);

    public abstract K getKey(V value);

    public abstract <X extends Throwable> V getValueOrThrow(K key, Supplier<X> thrower) throws X;

    public abstract <X extends Throwable> K getKeyOrThrow(V value, Supplier<X> thrower) throws X;

    public abstract <X extends Throwable> V computeValueIfAbsent(K key, ThrowingFunction<K, V, X> factory) throws X;

    public abstract <X extends Throwable> K computeKeyIfAbsent(V value, ThrowingFunction<V, K, X> factory) throws X;

    public abstract Optional<V> findValue(K key);

    public abstract Optional<K> findKey(V value);

    public abstract V getValueOrDefault(K key, V defaultValue);

    public abstract K getKeyOrDefault(V value, K defaultKey);

    public abstract void forEach(BiConsumer<? super K, ? super V> action);

    public abstract void put(K key, V value);

    public abstract void putIfAbsent(K key, V value);

    public abstract V removeKey(K key);

    public abstract K removeValue(V v);

    public abstract void putAll(Map<? extends K, ? extends V> m);

    public abstract void clear();

    public abstract boolean remove(K key, V value);

    /**
     * Create a new bidirectional map by constructing backing maps from the
     * provided supplier.
     * <p>
     * Backing maps needn't be concurrent as the BidirectionalMap will ensure
     * thread safety.
     *
     * @param <K>
     * @param <V>
     * @param mapFactory
     * @return
     */
    public static <K, V> BidirectionalMap<K, V> newMap(
            Supplier<Map> mapFactory) {
        return new LockingBidirectionalMap(mapFactory.get(), mapFactory.get());
    }

    private static class LockingBidirectionalMap<K, V> extends BidirectionalMap<K, V> {

        private final Map<K, V> keyIndex;
        private final Map<V, K> valueIndex;
        private final ReentrantReadWriteLock mapLock;

        private LockingBidirectionalMap(Map<K, V> keyIndex, Map<V, K> valueIndex) {
            this.keyIndex = keyIndex;
            this.valueIndex = valueIndex;
            this.mapLock = new ReentrantReadWriteLock();
        }

        @Override
        public int size() {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return keyIndex.size();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean isEmpty() {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return keyIndex.isEmpty();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean containsKey(K key) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return keyIndex.containsKey(key);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean containsValue(V value) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return valueIndex.containsKey(value);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public V getValue(K key) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return keyIndex.get(key);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public K getKey(V value) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return valueIndex.get(value);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Optional<K> findKey(V value) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return Optional.ofNullable(valueIndex.get(value));
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Optional<V> findValue(K key) {

            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return Optional.ofNullable(keyIndex.get(key));
            } finally {
                lock.unlock();
            }
        }

        @Override
        public V getValueOrDefault(K key, V defaultValue) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return keyIndex.getOrDefault(key, defaultValue);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public K getKeyOrDefault(V value, K defaultKey) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                return valueIndex.getOrDefault(value, defaultKey);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                keyIndex.forEach(action);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void put(K key, V value) {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                keyIndex.put(key, value);
                valueIndex.put(value, key);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                m.forEach((k, v) -> this.put(k, v));
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void putIfAbsent(K key, V value) {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                if (!keyIndex.containsKey(key)) {
                    put(key, value); //elevates to write lock
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        public V removeKey(K key) {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                V value = keyIndex.remove(key);
                valueIndex.remove(value);
                return value;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public K removeValue(V v) {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                K key = valueIndex.remove(v);
                keyIndex.remove(key);
                return key;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void clear() {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                keyIndex.clear();
                valueIndex.clear();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean remove(K key, V value) {
            WriteLock lock = mapLock.writeLock();
            try {
                lock.lock();
                keyIndex.remove(key, value);
                valueIndex.remove(value, key);
            } finally {
                lock.unlock();
            }
            return keyIndex.remove(key, value);
        }

        @Override
        public <X extends Throwable> V getValueOrThrow(K key,
                Supplier<X> thrower) throws X {
            return this.findValue(key).orElseThrow(thrower);
        }

        @Override
        public <X extends Throwable> K getKeyOrThrow(V value,
                Supplier<X> thrower) throws X {
            return this.findKey(value).orElseThrow(thrower);
        }

        @Override
        public <X extends Throwable> V computeValueIfAbsent(K key,
                ThrowingFunction<K, V, X> factory) throws X {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                if (keyIndex.containsKey(key)) {
                    return keyIndex.get(key);
                }
                V value = factory.apply(key);
                this.put(key, value);
                return value;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public <X extends Throwable> K computeKeyIfAbsent(V value,
                ThrowingFunction<V, K, X> factory) throws X {
            ReadLock lock = mapLock.readLock();
            try {
                lock.lock();
                if (valueIndex.containsKey(value)) {
                    return valueIndex.get(value);
                }
                K key = factory.apply(value);
                this.put(key, value);
                return key;
            } finally {
                lock.unlock();
            }
        }

    }

}
