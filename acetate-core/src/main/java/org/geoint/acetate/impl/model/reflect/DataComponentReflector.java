package org.geoint.acetate.impl.model.reflect;

import java.util.Collection;
import java.util.concurrent.Callable;
import org.geoint.acetate.model.ComponentModel;

/**
 * Creates a data model component for the provided class using reflection.
 *
 * @param <T>
 */
public class DataComponentReflector<T>
        implements Callable<Collection<ComponentModel<?>>> {

    private final Class<T> clazz;

    public DataComponentReflector(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Collection<ComponentModel<?>> call() {

    }

}
