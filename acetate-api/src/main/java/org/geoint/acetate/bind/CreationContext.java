package org.geoint.acetate.bind;

/**
 * Defines the context to create the object instance.
 *
 * @param <T> object type to create
 */
public interface CreationContext<T> {

    /**
     * Java class type to create.
     *
     * @return class type to create
     */
    Class<T> getType();

}
