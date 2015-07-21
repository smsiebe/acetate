package org.geoint.acetate.model.provider;

import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.model.ModelElement;

/**
 * Used as a supplier of {@link ModelElement model elements} by the
 * {@link MetaModelRegistry}, the provider interface abstracts the discovery of
 * model elements process, allowing applications to provide custom provider
 * implementations based on their environmental needs.
 * <p>
 * MetaModelProviders provide ModelElement instances based on its discovery
 * process, which provides accessors to the AnnotatedElement instance. This
 * means that providers provide Class/Method/Field, etc implementations for the
 * model, allowing the provider do interesting things like providing proxy
 * implementations or using a custom class loader.
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
 * Loading the ModelProvider via a ServiceLoader, rather than a metamodel 
 * defined provider for example, was a decision made to keep the acetate-meta 
 * project focused on providing metamodeling and model implementations.
 */
@FunctionalInterface
public interface ModelProvider {

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
