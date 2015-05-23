package org.geoint.acetate.model;

/**
 * Indicates that the model component is inheritable.
 *
 *
 */
public interface InheritableComponent {

    /**
     * Determines if the model component instance was inherited or if it was
     * locally defined on the model component.
     *
     * @return true if the instance was inherited, false if the component was
     * locally defined
     */
    boolean isInherited();

    /**
     * Indicates if the component instance must be inherited, or if it should be
     * ignored by inheritance (default is true).
     *
     * @return true if the component is inherited, otherwise false
     */
    default boolean inherit() {
        return true;
    }

}
