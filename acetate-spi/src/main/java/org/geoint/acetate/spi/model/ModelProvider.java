package org.geoint.acetate.spi.model;

import java.util.ServiceLoader;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.acetate.metamodel.ModelException;

/**
 * Acetate plugin interface for providers which can resolve data models for a
 * class/object.
 *
 * <h1>Loading Providers</h1>
 * ModelProvider plugins are loaded with the {@link ServiceLoader}. If there are
 * multiple model providers discovered by the ServiceLoader, the first provider
 * that can model this class is used. To specify which provider to use, see
 * <i>Resolving Multiple Providers</i> below.
 *
 * <h2>Resolving Multiple Providers</h2>
 * When a specific ModelProvider is desired to resolve classes you may specify
 * the specific provider to use by setting the
 * <i>acetate.model.provider.resolver.class</i> JVM property to a class which
 * implements the {@link ProviderResolver} interface.
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

}
