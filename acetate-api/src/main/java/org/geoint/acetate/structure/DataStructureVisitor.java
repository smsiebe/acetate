package org.geoint.acetate.structure;

import org.geoint.acetate.model.DataComponent;

/**
 * Visitor used to hierarchically traverse a {@link DataStructure}.
 *
 */
public interface DataStructureVisitor {

    /**
     * Called for a data component for which there may only be, at most, a
     * single instance of the component.
     *
     * @param position dot-separated hierarchical position
     * @param component data structure component
     */
    void component(String position, DataComponent<?> component);

    /**
     * Called for a data component which may have more than one instance at the
     * designated position. In other words, the model supports an array
     * (collection) of these components.
     *
     * @param position dot-separated hierarchical position
     * @param component component type supported by this collection
     */
    void array(String position, DataComponent<?> component);

    /**
     * Called when the structure key/value maps zero or more pairs of data
     * components. In other words, the model supports a map.
     *
     * @param position dot-separated hierarchical position
     * @param key data component type used as a key
     * @param value data component type used as a value
     */
    void map(String position, DataComponent<?> key, DataComponent<?> value);

}
