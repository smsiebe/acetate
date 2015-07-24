package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import org.geoint.acetate.model.meta.Meta;
import org.geoint.acetate.model.provider.LazyClassLoaderResolver;
import org.geoint.acetate.model.provider.Resolver;

/**
 * A model annotation is defined by being annotated with {@link Meta}.
 * <p>
 * A model annotation is application defined, used in defining the domain model
 * components. The metamodel framework uses these model annotations to provide
 * discovery of these model components through the {@link ModelRegistry}.
 *
 * @param <A>
 * @see Meta
 */
public class ModelAnnotation<A extends Annotation>
        extends ModelElement<Class<A>> implements Annotation {

    private final String annotationClassName;

    public ModelAnnotation(String annotationClassName,
            ModelAnnotation<?>[] modelAnnotations,
            Class<A> element) {
        super(modelAnnotations, element);
        this.annotationClassName = annotationClassName;
    }

    public ModelAnnotation(String annotationClassName,
            ModelAnnotation<?>[] modelAnnotations) {
        super(modelAnnotations, 
                new LazyClassLoaderResolver(annotationClassName));
        this.annotationClassName = annotationClassName;
    }

    public ModelAnnotation(String annotationClassName,
            ModelAnnotation<?>[] modelAnnotations,
            Resolver<Class<A>> elementResolver) {
        super(modelAnnotations, elementResolver);
        this.annotationClassName = annotationClassName;
    }

    /**
     * Returns the the ModelAnnotation for this annotation type, if it is a
     * model annotation itself.
     *
     * @param <A>
     * @param annotation
     * @return model of annotation or null if the annotation is not annotated
     * with metamodel annotations
     */
    public static <A extends Annotation> ModelAnnotation<A> reflect(
            Class<A> annotation) {
        ModelAnnotation[] metaAnnotations
                = Arrays.stream(annotation.getAnnotations())
                .filter(ModelAnnotation::isMetaModel)
                .map((a) -> reflect(a.annotationType())) //TODO fix eternal loop
                .toArray((i) -> new ModelAnnotation[i]);
        return new ModelAnnotation(annotation.getName(),
                metaAnnotations,
                () -> annotation);
    }

    public static boolean isMetaModel(Annotation a) {
        return a.annotationType().isAnnotationPresent(Meta.class);
    }

    /**
     * Name of the model annotation class.
     *
     * @return model annotation class name
     */
    public String getAnnotationName() {
        return annotationClassName;
    }

    @Override
    public <A extends Annotation> ModelAnnotation<A>[] getModelAnnotations(
            Class<A> modelAnnotationClass) {
        final String cn = modelAnnotationClass.getName();
        return Arrays.stream(getModelAnnotations())
                .filter((a) -> a.annotationClassName.contentEquals(cn))
                .toArray((i) -> new ModelAnnotation[i]);
    }

    @Override
    public ModelAnnotation<?>[] getModelAnnotations() {
        return modelAnnotations;
    }

    @Override
    public ModelAnnotation<?>[] getDeclaredModelAnnotations() {
        return Arrays.stream(getDeclaredAnnotations())
                .filter(ModelAnnotation::isMetaModel)
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

    @Override
    public Class<? extends Annotation> annotationType() {
        return resolver.resolve();
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.visit(this);
    }
}
