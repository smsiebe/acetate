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

    <T> DataModel<T> model(Class<T> clazz) throws ModelException;

    <T> MutableModel<T> mutableModel(Class<T> clazz) throws ModelException;

}
