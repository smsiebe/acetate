package org.geoint.acetate.model.provider;

/**
 * Resolves an object.
 *
 * @param <T>
 */
@FunctionalInterface
public interface Resolver<T> {

    T resolve();
}
