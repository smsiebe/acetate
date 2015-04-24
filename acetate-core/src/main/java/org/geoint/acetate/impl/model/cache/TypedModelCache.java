package org.geoint.acetate.impl.model.cache;

import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.geoint.acetate.model.DataComponent;

/**
 * Caches data model components for java types.
 *
 * All implementations of TypedModelCache must be thread safe.
 */
public interface TypedModelCache {

    /**
     * Returns the model component, if already cached, otherwise synchronously
     * creates the DataComponent on the current thread.
     *
     * @param <T> component type
     * @param type component class
     * @param creator component creator
     * @return cached data component
     */
    <T> DataComponent<T> getOrCache(Class<T> type,
            Supplier<Callable<DataComponent<T>>> creator);

    /**
     * Sets the model component, returning the old component or null.
     *
     * @param <T>
     * @param type
     * @param component
     * @return old component from cache or null if there wasn't a component
     * previously
     */
    <T> DataComponent<T> put(Class<T> type, DataComponent<T> component);
}
