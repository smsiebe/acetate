package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a contextual "view" of the domain model, altering the domain model
 * definition of the model component the View annotates.
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
    Component[] components() default {};
}
