package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Optionally annotates a models method, overridding default values.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Operation {

    /**
     * Overrides the operation name, which is normally the method name.
     *
     * @return explicit operation name
     */
    String name() default "";

    /**
     * Overrides the operations description.
     *
     * @return explicit operation description
     */
    String description() default "";

}
