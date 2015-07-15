package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a {@link EventModel event} of a domain model.
 */
@Documented
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {

    /**
     * Name of the domain that defines this event.
     *
     * @return domain name of the event
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
