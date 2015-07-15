package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Optional annotation used to describe the composite model meta.
 * <p>
 * This annotation is used on a single-valued composite model.  When a composite 
 * is multi-valued, use the {@link MultiComposite} annotation instead.
 * 
 * @see MultiComposite
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@Documented
public @interface Composite {

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
