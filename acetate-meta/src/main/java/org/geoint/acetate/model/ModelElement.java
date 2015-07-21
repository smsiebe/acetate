package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.geoint.acetate.model.meta.Meta;
import org.geoint.acetate.model.provider.ModelProvider;

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
 * @see ModelProvider
 */
public interface ModelElement extends AnnotatedElement {

    /**
     * ModelAnnotation definitions for the requested model annotation type.
     *
     * @param <A> model annotation type
     * @param modelAnnotationClass
     * @return requested model annotation definition(s) on this element
     */
    <A extends Annotation> ModelAnnotation<A>[] getModelAnnotations(
            Class<A> modelAnnotationClass);

    /**
     * All model annotations defined for this element.
     *
     * @return all model annotations for this element
     */
    ModelAnnotation<?>[] getModelAnnotations();

    /**
     * Model annotations defined directly by this element.
     *
     * @return model annotations declared directly by this element, otherwise
     * return an empty array
     */
    ModelAnnotation<?>[] getDeclaredModelAnnotations();

}
