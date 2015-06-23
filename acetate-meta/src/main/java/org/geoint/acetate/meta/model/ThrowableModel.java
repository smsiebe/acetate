package org.geoint.acetate.meta.model;

/**
 * Model of a Throwable type.
 *
 * @param <T>
 */
public interface ThrowableModel<T extends Throwable> {

    Class<T> getExceptionClass();

}
