package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.ModelProvider;

/**
 *
 */
public final class ModelRegistry {

    private static final ModelRegistry INSTANCE = new ModelRegistry();
    private final Collection<ModelProvider> providers;

    public ModelRegistry() {
        providers = Collections.synchronizedList(new ArrayList<>());

        ServiceLoader.load(ModelProvider.class)
                .forEach((p) -> providers.add(p));
    }

    public static ModelRegistry getRegistry() {
        return INSTANCE;
    }

    public void register(ModelProvider provider) {
        providers.add(provider);
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
        final Set<String> aNames = Arrays.stream(modelAnnotations).
                map((c) -> c.getCanonicalName())
                .collect(Collectors.toSet());

        
        return providers.parallelStream()
                .flatMap((p) -> p.getModelElements().stream())
                .filter((m)
                        -> Arrays.stream(m.getModelAnnotations())
                        .anyMatch((a) -> aNames.contains(a.getAnnotationName()))
                )
                /* use standard set and let the ModelElement#equals define 
                 * equality
                 */
                .collect(Collectors.toSet());

    }

    public Collection<ModelType> getSubclasses(Class<?> modelType) {
        final String parentClassName = modelType.getCanonicalName();
        Optional<ModelType> parentModel = providers.parallelStream()
                .flatMap((p) -> p.getModelElements().stream())
                .filter((m) -> m instanceof ModelType)
                .map((m) -> (ModelType) m)
                .filter((m) -> m.getTypeName().contentEquals(parentClassName))
                .findFirst();
        return parentModel.isPresent()
                ? Arrays.asList(parentModel.get().getSubclasses())
                : Collections.EMPTY_LIST;
    }

    public Collection<ModelType> getAnnotatedSubclass(
            Class<? extends Annotation> modelAnnotation) {
        return getAnnotated(modelAnnotation).parallelStream()
                .filter((m) -> m instanceof ModelType)
                .map((m) -> (ModelType) m)
                .flatMap((m) -> Arrays.stream(m.getSubclasses()))
                .collect(Collectors.toSet());
    }

}
