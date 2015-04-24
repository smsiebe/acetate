package org.geoint.acetate.model.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Overrides default model meta data for a model component.
 *
 */
@Documented
@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    /**
     * Model overrides for components within this model.
     *
     * @return component-specific configuration
     */
    ModelComponent[] components() default {};
}
