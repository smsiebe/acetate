package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a domain or a domain model that has been deprecated from the
 * domain model.
 * <p>
 * This annotation is added (does not replace) to the model definition
 * (type/method) to indicate it is no longer supported by the model.
 * <p>
 * <b>NOTE:</b> <i>Removing</i> a model from an existing domain is not
 * supported. The only want to remove a model is by renaming (creating a new)
 * domain.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Deprecated {

    /**
     * Version of the domain model that this model was deprecated.
     *
     * @return domain version this model was deprecated
     */
    String version();

    /**
     * OPTIONAL comment instructing domain model users about this deperecated
     * status.
     *
     * @return deprecated comment (may be an empty string)
     */
    String comment() default "";
}
