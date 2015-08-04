package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies a method as providing idempotent, safe, access to data from a
 * composite model.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@MetaModel
public @interface Accessor {

    /**
     * OPTIONAL alternate, container-model unique, name to use for the accessor.
     *
     * By default, the name of the method is used.
     *
     * @return container-model unique accessor name
     */
    String name() default "";

    /**
     * OPTIONAL display name of the accessor; unspecified the display name is
     * the same as the {@link #name() } of the model.
     *
     * @return display name of the accessor
     */
    String displayName() default "";
}
