package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Explicitly identifies a method as an object behavior/operation.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {

    /**
     * Optional - Name of the operation, which must be object-unique.
     *
     * Setting this attribute overrides the default name provided by acetate.
     *
     * @return operation name override
     */
    String name() default "";

    /**
     * Optional - A human-readable description of the operation.
     *
     * @return optional human-readable operation description
     */
    String description() default "";

    /**
     * Optional - Indicate if the operation should be inherited by specialized
     * objects, default is true.
     *
     * @return true if inherit (default)
     */
    boolean inherit() default true;
}
