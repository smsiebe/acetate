package org.geoint.acetate.model;

/**
 * Indicates that the model component is inheritable.
 */
public interface Inheritable {

    /**
     * Indicates if the component must be inherited, or if it should be ignored
     * by inheritance (default is true).
     *
     * @return true if the component is inherited, otherwise false
     */
    default boolean inherit() {
        return true;
    }

}
