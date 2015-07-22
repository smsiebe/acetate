package org.geoint.acetate.model.provider;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import org.geoint.acetate.model.ModelElement;
import org.geoint.acetate.model.ModelRegistry;
import org.geoint.acetate.model.ModelType;

/**
 * Dynamic model registry which relies on underlying providers for model
 * elements.
 */
public final class ProviderRegistry extends ModelRegistry {

    private static final ProviderRegistry INSTANCE = new ProviderRegistry();
    private final Collection<ModelProvider> providers;

    public ProviderRegistry() {
        providers = Collections.synchronizedList(new ArrayList<>());

        ServiceLoader.load(ModelProvider.class)
                .forEach((p) -> providers.add(p));
    }

    public static ProviderRegistry getRegistry() {
        return INSTANCE;
    }

    public void register(ModelProvider provider) {
        providers.add(provider);
    }

    @Override
    public Collection<ModelElement> getModels() {
        return providers.parallelStream()
                .flatMap((p) -> p.getModelElements().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Return all the model components that are annotated by at least one of the
     * provided model annotations.
     *
     * @param modelAnnotations
     * @return
     */
    @Override
    public Collection<ModelElement> getAnnotated(
            Class<? extends Annotation>... modelAnnotations) {
        return Arrays.stream(modelAnnotations)
                .flatMap((a) -> providers.parallelStream()
                        .flatMap((p) -> p.getModelElements().stream()
                                .filter((m) -> m.isAnnotationPresent(a))))
                .collect(Collectors.toList());
    }

    @Override
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

}
