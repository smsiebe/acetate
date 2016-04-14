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

import java.util.function.Supplier;

/**
 * Returns the same instance each time the {@link SingletonSupplier#get()}
 * method is called.
 * <p>
 * Instances of this class must be thread-safe.
 *
 * @author steve_siebert
 * @param <T> singleton type
 */
public abstract class SingletonSupplier<T> {

    protected T singleton;

    protected SingletonSupplier() {
    }

    protected SingletonSupplier(T singleton) {
        this.singleton = singleton;
    }

    /**
     * Returns a singleton supplier that will always return the instance
     * provided to this method.
     *
     * @param <T>
     * @param singleton
     * @return
     */
    public static <T> SingletonSupplier<T> newSingleton(T singleton) {
        if (singleton == null) {
            throw new NullPointerException("Singleton must not be null.");
        }
        return new SingletonSupplier(singleton) {
        };
    }

    /**
     * Returns the instance provided by this supplier.
     *
     * @param <T>
     * @param supplier called at most once
     * @return
     */
    public static <T> SingletonSupplier<T> newSingleton(Supplier<T> supplier) {
        return new DeferredSingletonSupplier(supplier);
    }

    /**
     * Retrieve the instance.
     *
     * @return singleton instance
     */
    public T get() {
        return singleton;
    }

    private static final class DeferredSingletonSupplier<T> extends SingletonSupplier<T> {

        private final Supplier<T> supplier;

        /**
         * Returns the instance provided by this supplier.
         *
         * @param supplier called at most once
         */
        public DeferredSingletonSupplier(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            synchronized (this) {
                if (singleton == null) {
                    singleton = supplier.get();
                }
            }
            return singleton;
        }

    }
}
