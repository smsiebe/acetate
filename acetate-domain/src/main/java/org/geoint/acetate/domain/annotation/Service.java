package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a {@link ServiceModel service class of the domain model}.
 *
 * @see ServiceModel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Service {

    /**
     * Name of the domain that defines this model.
     *
     * @return domain model name
     */
    String domain();

    /**
     * OPTIONAL domain-unique "short" name of the model; unspecified the name is
     * derived from the domain and type name.
     *
     * @return name of the model
     */
    String name() default "";

    /**
     * OPTIONAL display name of the model; unspecified the display name is the
     * same as the {@link #name() } of the model.
     *
     * @return display name of the model
     */
    String displayName() default "";

    /**
     * OPTIONAL version this model was first added to the domain; unspecified
     * indicates the model was present since the first version of the domain.
     *
     * @see ModelVersion
     * @return domain model version
     */
    String version() default "";
}
