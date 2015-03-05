package org.geoint.acetate.spi.model;

import java.util.ServiceLoader;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.ModelException;
import org.geoint.acetate.metamodel.MutableModel;

/**
 * Acetate plugin interface for providers which can resolve data models for a
 * class/object.
 *
 * ModelProvider plugins are loaded with the {@link ServiceLoader}.
 */
public interface ModelProvider {

    /**
     * Creates a data model from the provided class.
     *
     * @param <T> data model type
     * @param clazz class from which to model
     * @return the data model
     * @throws ModelException thrown if the provider could not model the
     * provided class
     */
    <T> DataModel<T> model(Class<T> clazz) throws ModelException;

    /**
     * Creates a mutable model based on the provided class.
     *
     * The returned model can be changed programmatically.
     *
     * @param <T> data model type
     * @param clazz class from which to model
     * @return the data model
     * @throws ModelException thrown if the provider could not model the
     * provided class
     */
    <T> MutableModel<T> mutableModel(Class<T> clazz) throws ModelException;

}
