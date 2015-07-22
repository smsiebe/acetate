package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Registry of model elements.
 */
public abstract class ModelRegistry {

    /**
     * Returns all registered model elements.
     *
     * @return all registered model elements
     */
    public abstract Collection<ModelElement> getModels();

    /**
     * Return all the model components that are annotated by at least one of the
     * provided model annotations.
     *
     * @param modelAnnotations
     * @return model elements annotated with the specified model annotation
     */
    public abstract Collection<ModelElement> getAnnotated(
            Class<? extends Annotation>... modelAnnotations);

    /**
     * Return all subclasses of a specified class/interface.
     *
     *
     * @param modelType
     * @return collection of subclasses or an empty collection if there are no
     * subclasses or the specified class is not annotated with a metamodel
     * annotation
     */
    public abstract Collection<ModelType> getSubclasses(Class<?> modelType);

    /**
     * Return all classes annotated with the specified model annotation.
     *
     * @param modelAnnotation
     * @return collection of classes annotated with the specified model
     * annotation, or empty collection if there are no class or the provided
     * annotation is not managed by the metamodel
     */
    public Collection<ModelType> getAnnotatedClasses(
            Class<? extends Annotation> modelAnnotation) {
        return getAnnotated(modelAnnotation).parallelStream()
                .filter((m) -> m instanceof ModelType)
                .map((m) -> (ModelType) m)
                .flatMap((m) -> Arrays.stream(m.getSubclasses()))
                .collect(Collectors.toSet());
    }

}
