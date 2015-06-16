package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The View annotation allows developers to define a contextually augmented 
 * "view" of a domain model component, providing a means to alter the domain 
 * model within this declared context.
 *
 */
@Documented
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

    /**
     * View overrides for components within this model.
     *
     * @return component-specific configuration
     */
    ComponentView[] components() default {};
}
