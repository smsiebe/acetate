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
package org.geoint.acetate.functional;

import org.geoint.acetate.model.resolve.UnresolvedException;

/**
 *
 * @author steve_siebert
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface Resolver<K, V> {

    /**
     * Check if resolver can resolve the requested key.
     *
     * @param key
     * @return true if the resolver can provide a value, otherwise false
     */
    default boolean canResolve(K key) {
        try {
            resolve(key);
            return true;
        } catch (UnresolvedException ex) {
            return false;
        }
    }

    /**
     * Attempts to resolve locally, failing back to the provided resolver, and
     * finally throwing an exception if the value could not be resolved.
     *
     * @param key
     * @param resolver
     * @return
     * @throws UnresolvedException
     */
    default V resolveOrElse(K key, Resolver<K, V> resolver)
            throws UnresolvedException {
        try {
            return resolve(key);
        } catch (UnresolvedException ex) {
            return resolver.resolve(key);
        }
    }

    /**
     * Returns a new chained resolver which attempts to resolver using the
     * provided resolver then tries to resolve with this resolver.
     *
     * @param tryFirst resolver to try first
     * @return new chained resolver
     */
    default Resolver<K, V> before(Resolver<K, V> tryFirst) {
        return (k) -> tryFirst.resolveOrElse(k, this);
    }

    /**
     * Returns a new chained resolver which attempts to resolve using this
     * resolver then attempts to resolve with the provied resolver.
     *
     * @param tryAfter resolver to try after this
     * @return new chained resolver
     */
    default Resolver<K, V> after(Resolver<K, V> tryAfter) {
        return (k) -> this.resolveOrElse(k, tryAfter);
    }

    /**
     * Attempts to return the value for the requested key.
     *
     * @param key mapped key to resolve
     * @return resolved value
     * @throws UnresolvedException if the resolver cannot return a value for the
     * provided key
     */
    V resolve(K key) throws UnresolvedException;
}
