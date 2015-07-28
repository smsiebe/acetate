package org.geoint.metamodel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.metamodel.xml.ModelDescriptor;

/**
 * Registry of meta-model elements loaded from the
 * {@link ModelDescriptor meta-model descriptor}.
 */
public final class ModelRegistry {

    private static final ModelRegistry LOCAL = ModelRegistry.loadLocal();
    public static final ModelRegistry EMPTY = new ModelRegistry(
            Collections.EMPTY_MAP,
            Collections.EMPTY_MAP,
            Collections.EMPTY_MAP
    );

    private final Map<Class<? extends Annotation>, Set<Class<?>>> metamodelClasses;
    private final Map<Class<? extends Annotation>, Set<Method>> metamodelMethods;
    private final Map<Type, Set<Class<?>>> subclassIndex;

    private ModelRegistry(Map<Class<? extends Annotation>, Set<Class<?>>> metamodelClasses,
            Map<Class<? extends Annotation>, Set<Method>> metamodelMethods,
            Map<Type, Set<Class<?>>> subclassIndex) {
        this.metamodelClasses = metamodelClasses;
        this.metamodelMethods = metamodelMethods;
        this.subclassIndex = subclassIndex;
    }

    /**
     * Load the model registry from the local jar descriptor.
     *
     * @return local jar model registry
     */
    private static ModelRegistry loadLocal() {
        return ModelDescriptor.loadRegistry();
    }

    public static ModelRegistry getLocalRegistry() {
        return LOCAL;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * All meta-model annotated annotations known to this registry.
     *
     * @return all known meta-model annotated annotations
     */
    public Collection<Class<? extends Annotation>> getModelAnnotations() {
        Collection<Class<? extends Annotation>> modelAnnotations
                = new HashSet<>();
        modelAnnotations.addAll(metamodelClasses.keySet());
        modelAnnotations.addAll(metamodelMethods.keySet());
        return modelAnnotations;
    }

    /**
     * All meta-model annotated types (classes/interfaces) known to this
     * registry.
     *
     * @return all meta-model annotated types
     */
    public Collection<Class<?>> getModelTypes() {
        return getAllIndexed(metamodelClasses);
    }

    /**
     * Return all classes and interfaces annotated with the specified model
     * annotation.
     *
     * @param modelAnnotation
     * @return collection of classes/interfaces annotated with the specified
     * model annotation, or empty collection if there are no class or the
     * provided annotation is not managed by the metamodel
     */
    public Collection<Class<?>> getModelTypes(
            Class<? extends Annotation> modelAnnotation) {
        return getIndexed(modelAnnotation, metamodelClasses);
    }

    /**
     * Return all meta-model annotated methods known to the registry.
     *
     * @return all meta-model annotated methods
     */
    public Collection<Method> getModelMethods() {
        return getAllIndexed(metamodelMethods);
    }

    /**
     * Return all meta-model annotated methods annotated with the specified
     * metamodel annotation known to the registry.
     *
     * @param modelAnnotation
     * @return all meta-model methods annotated with the specified model
     * annotation
     */
    public Collection<Method> getModelMethods(
            Class<? extends Annotation> modelAnnotation) {
        return getIndexed(modelAnnotation, metamodelMethods);
    }

    /**
     * Retrieve all (meta-model annotated) subclasses for the specified model
     * type.
     *
     * @param modelClass
     * @return
     */
    public Collection<Class<?>> getSubclasses(Type modelClass) {
        return getIndexed(modelClass, subclassIndex);
    }

    private <I, V> Set<V> getIndexed(I indexKey,
            Map<I, Set<V>> index) {
        return !index.containsKey(indexKey)
                ? Collections.EMPTY_SET
                : Collections.unmodifiableSet(index.get(indexKey));
    }

    private <V> Collection<V> getAllIndexed(Map<?, Set<V>> index) {
        return index.values().stream().flatMap((c) -> c.stream())
                .collect(Collectors.toSet());
    }

    /**
     * Not thread-safe.
     */
    public static class Builder {

        private ModelRegistry registry = new ModelRegistry(
                new HashMap<>(), new HashMap<>(), new HashMap<>()
        );

        public Builder model(Class<? extends Annotation> modelAnnotation,
                Class<?> type) {

            index(registry.metamodelClasses, modelAnnotation, type);
            indexSuperInterfaces(modelAnnotation, type);
            indexSuperclass(modelAnnotation, type);
            return this;
        }

        public Builder model(Class<? extends Annotation> modelAnnotation,
                Method method) {
            index(registry.metamodelMethods, modelAnnotation, method);
            return this;
        }

        private <I, V> void index(Map<I, Set<V>> index, I key, V value) {
            if (!index.containsKey(key)) {
                index.put(key, new HashSet<>());
            }
            index.get(key).add(value);
        }

        /**
         * Adds subclass indexing for the interfaces of a type.
         *
         * @param modelAnnotation
         * @param subclass
         */
        private void indexSuperInterfaces(Class<? extends Annotation> modelAnnotation,
                Class<?> subclass) {
            Arrays.stream(subclass.getAnnotatedInterfaces())
                    .filter((i) -> i.isAnnotationPresent(modelAnnotation))
                    .forEach((i) -> index(registry.subclassIndex, i.getType(), subclass));
        }

        /**
         * Add subclass indexing for parent types which are annotated with the
         * specified annotation.
         *
         * @param modelAnnotation
         * @param subclass
         */
        private void indexSuperclass(Class<? extends Annotation> modelAnnotation,
                Class<?> subclass) {
            //interfaces 
        }

        public ModelRegistry build() {
            try {
                return registry;
            } finally {
                registry = null;
            }
        }
    }
}
