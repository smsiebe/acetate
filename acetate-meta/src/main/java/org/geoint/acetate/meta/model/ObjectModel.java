package org.geoint.acetate.meta.model;

import java.util.Collection;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Object metadata as identified by the acetate metamodel framework.
 *
 * @param <T> java class this object meta is models
 */
public interface ObjectModel<T> {

    /**
     * Java object this meta models.
     *
     * @return java type
     */
    Class<T> getObjectType();

    /**
     * Version of the object model.
     *
     * @return object model version
     */
    MetaVersion getVersion();

    /**
     * Object operations.
     *
     * @return object operation meta
     */
    Collection<OperationModel> getOperations();

    /**
     * Object model classes from which this model extends.
     *
     * @return parent object models
     */
    Collection<ObjectModel<? super T>> getParents();

    /**
     * Object model classes that extends this model.
     *
     * @return specialized types of this model
     */
    Collection<ObjectModel<? extends T>> getSpecialized();

}
