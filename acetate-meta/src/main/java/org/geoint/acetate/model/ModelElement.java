package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import org.geoint.acetate.model.meta.Meta;
import org.geoint.acetate.model.provider.ModelProvider;
import org.geoint.acetate.model.provider.Resolver;

/**
 * Model component identified by being annotated by a model annotation (which
 * itself is identified by being annotated with the {@link Meta} annotation).
 * <p>
 * As acetate uses annotations to declare model components, model elements are
 * inherently AnnoatedElelements. While model-defined annotations are special to
 * application frameworks and generic metamodel processors, the model elements
 * are special in that they also provide a means for
 * {@link ModelProvider providers} to load the model classes, rather than just
 * the default/system Classloader.
 *
 * @param <E>
 * @see ModelProvider
 */
public abstract class ModelElement<E extends AnnotatedElement>
        implements AnnotatedElement {

    protected final ModelAnnotation<?>[] modelAnnotations;
    protected final Resolver<E> resolver;

    public ModelElement(ModelAnnotation<?>[] modelAnnotations,
            Resolver<E> elementResolver) {
        this.modelAnnotations = modelAnnotations;
        this.resolver = elementResolver;
    }

    public ModelElement(ModelAnnotation<?>[] modelAnnotations,
            E element) {
        this(modelAnnotations, () -> element);
    }

    /**
     * ModelAnnotation definitions for the requested model annotation type.
     *
     * @param <A> model annotation type
     * @param modelAnnotationClass
     * @return requested model annotation definition(s) on this element
     */
    public <A extends Annotation> ModelAnnotation<A>[] getModelAnnotations(
            Class<A> modelAnnotationClass) {
        final String annotationName = modelAnnotationClass.getName();
        return Arrays.stream(modelAnnotations)
                .filter((a) -> a.getAnnotationName().contentEquals(annotationName))
                .toArray((i) -> new ModelAnnotation[i]);
    }

    /**
     * All model annotations defined for this element.
     *
     * @return all model annotations for this element
     */
    public ModelAnnotation<?>[] getModelAnnotations() {
        return modelAnnotations;
    }

    /**
     * Model annotations defined directly by this element.
     *
     * @return model annotations declared directly by this element, otherwise
     * return an empty array
     */
    public ModelAnnotation<?>[] getDeclaredModelAnnotations() {
        return Arrays.stream(getDeclaredAnnotations())
                .flatMap((a) -> Arrays.stream(modelAnnotations)
                        .filter((ma) -> ma.annotationType()
                                .equals(a.annotationType())))
                .toArray((i) -> new ModelAnnotation[i]);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return resolver.resolve().getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return resolver.resolve().getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return resolver.resolve().getDeclaredAnnotations();
    }

    /**
     * Visits the model being notified for each model related to the model.
     *
     * @param visitor
     */
    public abstract void visit(ModelVisitor visitor);

}
