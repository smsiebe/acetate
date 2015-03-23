package org.geoint.acetate.metamodel;

import org.geoint.acetate.bind.BoundData;

/**
 * Metadata model of a Java Object.
 *
 * DataModel instances must be thread-safe.
 *
 * @param <D> class this models
 */
public interface DataModel<D> {

    /**
     * Binds a java object to this model.
     *
     *
     * @param instance object to bind against this model
     * @return bound data object
     */
    public BoundData<D> bind(D instance);

    /**
     * Retrieve a component model for any path within the data model.
     *
     * The field path is a period (.) delimited field name hierarchy, supporting
     * field aliases at any level.
     *
     * @param path fully qualified field path
     * @return model for the field, if no field returns null
     */
    ModelComponent<?> getComponent(String path);

    /**
     * The root model component.
     *
     * @return root model component
     */
    ModelClass<D> getModel();

}
