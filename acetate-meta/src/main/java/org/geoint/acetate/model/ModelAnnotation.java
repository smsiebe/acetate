package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import org.geoint.acetate.model.meta.Meta;

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
public interface ModelAnnotation<A extends Annotation>
        extends ModelElement, Annotation {

    /**
     * Name of the model annotation class.
     * 
     * @return model annotation class name
     */
    String getAnnotationName();
}
