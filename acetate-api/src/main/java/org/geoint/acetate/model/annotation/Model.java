package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines/augments/overrides a data model.
 *
 * Acetate uses these annotations to augment the data model it would use (or
 * generate) for the object this annotates.
 *
 * This annotation can be used to define a transient model (used once) or to
 * auto-register a model with the local model registry.
 */
@Documented
@Target({ElementType.TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    
    /**
     * Model overrides for components within this model.
     *
     * @return component-specific configuration
     */
    ModelComponent[] components() default {};
}
