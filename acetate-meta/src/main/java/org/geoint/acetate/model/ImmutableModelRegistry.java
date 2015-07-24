package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.model.provider.ProviderRegistry;

/**
 * An immutable, thread-safe, registry of model elements.
 *
 * @see ProviderRegistry
 */
public final class ImmutableModelRegistry extends ModelRegistry {

    private final Set<ModelElement> allModels;
    //key is annotation class name, value is collection of annotated models
    private final Map<String, Collection<ModelElement>> annotationIndex;
    //key is class name, value is subclasses of that type
    private final Map<String, Collection<ModelType>> subclassIndex;

    private static final Logger logger
            = Logger.getLogger(ImmutableModelRegistry.class.getName());

    /**
     *
     * @param allModels safe (ie defensive copy) collection of all element
     * models
     * @param annotationIndex safe (ie defensive copy) annotation index of the
     * models
     * @param subclassIndex safe (ie defensive copy) class index of subclasses
     */
    private ImmutableModelRegistry(Set<ModelElement> allModels,
            Map<String, Collection<ModelElement>> annotationIndex,
            Map<String, Collection<ModelType>> subclassIndex) {
        this.allModels = allModels;
        this.annotationIndex = annotationIndex;
        this.subclassIndex = subclassIndex;
    }

    public static ImmutableModelRegistry register(
            Collection<ModelElement> models) {
        //visit each model, inventoring all components, to ensure it's complete
        ModelInventoryVisitor inventory = new ModelInventoryVisitor();

        models.stream().forEach((e) -> {
            e.visit(inventory);
        });

        return new ImmutableModelRegistry(
                inventory.allModels,
                inventory.annotationIndex,
                inventory.subclassIndex);
    }

    @Override
    public Collection<ModelElement> getModels() {
        return Collections.unmodifiableCollection(allModels);
    }

    @Override
    public Collection<ModelElement> getAnnotated(
            Class<? extends Annotation>... modelAnnotations) {
        return Arrays.stream(modelAnnotations)
                .map((a) -> a.getName())
                .filter(annotationIndex::containsKey)
                .flatMap((a) -> annotationIndex.get(a).stream())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ModelType> getSubclasses(Class<?> modelClass) {
        final String cn = modelClass.getName();
        return (subclassIndex.containsKey(cn))
                ? Collections.unmodifiableCollection(subclassIndex.get(cn))
                : Collections.EMPTY_LIST;
    }

    /**
     * Thread-safe model visitor that inventories all the models.
     */
    private static class ModelInventoryVisitor implements ModelVisitor {

        final Set<ModelElement> allModels
                = Collections.synchronizedSet(new HashSet<>());
        final Map<String, Collection<ModelElement>> annotationIndex
                = Collections.synchronizedMap(new HashMap<>());
        final Map<String, Collection<ModelType>> subclassIndex
                = Collections.synchronizedMap(new HashMap<>());

        @Override
        public ModelVisitor.VisitorOption[] options() {
            return new ModelVisitor.VisitorOption[]{
                ModelVisitor.VisitorOption.NOTIFY_ONCE_PER_MODEL
            };
        }

        @Override
        public void visit(ModelElement model) {
            allModels.add(model);

            Arrays.stream(model.getModelAnnotations())
                    .forEach((a) -> index(annotationIndex,
                                    a.getAnnotationName(),
                                    model)
                    );

            if (model instanceof ModelType) {
                Arrays.stream(((ModelType) model).getSubclasses())
                        .forEach((st) -> index(subclassIndex,
                                        ((ModelType) model).getTypeName(),
                                        st)
                        );
            }
        }

        private synchronized <V> void index(Map<String, Collection<V>> index,
                String key, V value) {
            if (!index.containsKey(key)) {
                index.put(key, new ArrayList<>());
            }
            index.get(key).add(value);
        }
    }
}
