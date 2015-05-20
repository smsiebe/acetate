package gov.ic.geoint.acetate.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to identify an annotation as one which which provides context
 * for the construction of an Object.
 *
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataContextAnnotation {

    /**
     * Object type to create from the annotation.
     *
     * @return class of object type to create
     */
    Class<?> type();

    /**
     * Optional - The factory use to instantiate the ComponentAttribute
     * instance, if not a no-arg constructor.
     *
     * @return
     */
    Class<? extends AnnotationDataFactory> constructor()
            default NoArgAnnotationDataFactory.class;
}
