package org.geoint.acetate.metamodel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies an annotation as one that is used by the acetate data binding
 * framework.
 *
 * This annotation is used internally to help identify those annotations that
 * are of consequence to acetate.
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

}
