package org.geoint.acetate.model.meta.provider;

import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.model.meta.ModelElement;

/**
 * Implements the application-defied metamodel approach to discovering model
 * components.
 * <p>
 * Meta model providers are themselves discoverable via the
 * {@link ServiceLoader} or through null {@link MetaModelRegistry#register(MetaModelProvider) programmatic
 * registration with the registry}. By default the provided
 * {@link MetaAnnotationProcessor} will detect metamodel annotations, and the
 * models they annotate, at compile time and {@link MetaAnnotationProvider} will
 * load those discovered model components at runtime.
 * <p>
 * Providers are responsible for caching, if applicable, as the registry does
 * not.
 */
/*
 * Loading the MetaModelProvider via a ServiceLoader, rather than a metamodel 
 * defined provider for example, was a decision made to keep the acetate-meta 
 * project focused on providing metamodeling and model implementations.
 */
@FunctionalInterface
public interface MetaModelProvider {

    /**
     * Return all model elements discoverable through this provider.
     * <p>
     * The model registry will de-duplicate any duplicate models returned by
     * overlapping providers.
     *
     * @return discovered model elements
     */
    Collection<ModelElement> getModelElements();
}
