package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a method as providing idempotent, safe, access to data that is 
 * part of the domain model API, but not a part of the data model (not a 
 * composite object contained by the model).  
 * 
 * @see Accessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Query {

    /**
     * OPTIONAL alternate, container-model unique, name to use for the model in
     * this composite context.
     *
     * By default, the model name of the composite model is used as the
     * composite name.
     *
     * @return container-model unique composite name
     */
    String name() default "";

    /**
     * OPTIONAL display name of the model; unspecified the display name is the
     * same as the {@link #name() } of the model.
     *
     * @return display name of the model
     */
    String displayName() default "";
}
