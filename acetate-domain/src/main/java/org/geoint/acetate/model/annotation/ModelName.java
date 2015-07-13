package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Optional annotation used to explicitly define a models formatting and/or
 * display name.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModelName {

    /**
     * Explicitly set the name used for this model when formatting (binding) the
     * model.
     *
     * @return explicit formatting name for this model
     */
    String formatted() default "";

    /**
     * Explicitly set the display name of the model.
     *
     * @return explicit display name for model
     */
    String displayName() default "";

}
