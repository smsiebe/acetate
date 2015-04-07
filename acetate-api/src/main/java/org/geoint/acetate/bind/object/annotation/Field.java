package org.geoint.acetate.bind.object.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Explicitly identifies a method as one that should be considered as a data
 * field.
 *
 * This annotation can be applied to field methods that would otherwise be
 * interpreted as a field to explicity set the field meta data.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Field {

    /**
     * Explicity set the field name, changing the outer-most component path
     * token to this name.
     *
     * @return field name
     */
    String name() default "";

    /**
     * Optional brief description of the data field.
     *
     * @return field description
     */
    String description() default "";

}
