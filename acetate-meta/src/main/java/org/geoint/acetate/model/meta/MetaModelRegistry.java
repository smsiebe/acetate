package org.geoint.acetate.model.meta;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.model.meta.provider.MetaModelProvider;

/**
 *
 */
public final class MetaModelRegistry {

    private static final MetaModelRegistry INSTANCE = new MetaModelRegistry();
    private final Collection<MetaModelProvider> providers;
    private Map<String, Set<ModelElement>> annotatedModels; //key is annotation class name
    private Map<String, Set<ModelClass>> modelSubclasses; //key is parent class/interface name
    private Map<String, Set<ModelClass>> annotatedSubclasses; //key is annotation class name
    
    public MetaModelRegistry() {
        providers = new ArrayList<>();

        ServiceLoader.load(MetaModelProvider.class)
                .forEach((p) -> providers.add(p));
    }

    public static MetaModelRegistry getRegistry() {
        return INSTANCE;
    }

    public void register(MetaModelProvider provider) {
        synchronized (providers) {
            providers.add(provider);
        }
    }

    /**
     * Return all the model components that are annotated by at least one of the
     * provided model annotations.
     *
     * @param modelAnnotations
     * @return
     */
    public Collection<ModelElement> getAnnotated(
            Class<? extends Annotation>... modelAnnotations) {

    }

    public Collection<ModelElement> getAnnotated(
            Class<? extends Annotation> modelAnnotation) {

    }

    public Collection<ModelClass> getSubclasses(Class<?> modelType) {

    }
    
    public Collection<ModelClass> getAnnotatedSubclass(
            Class<? extends Annotation> modelAnnotation) {
        
    }
}
