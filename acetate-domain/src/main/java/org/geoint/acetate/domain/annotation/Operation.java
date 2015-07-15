package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a method as an {@link OperationModel operation}, which when
 * successfully executed changes the state of the related entity and returns an
 * {@link EventModel event} detailing the changes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Operation {

    /**
     * OPTIONAL name of the operation; unspecified will the same name of the
     * method.
     *
     * @return name of the operation or empty string if the operation uses the
     * same name as the annotated method
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
