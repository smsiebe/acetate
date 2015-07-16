package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the content type context.
 *
 * The ContentType annotation is used to define the supported content type of a
 * domain model component or to define the runtime context in which a model
 * component is being used.
 *
 * If not content type is defined, {@code application/binary} is assumed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
@Documented
@Repeatable(ContentTypes.class)
public @interface ContentType {

    String value();
}
