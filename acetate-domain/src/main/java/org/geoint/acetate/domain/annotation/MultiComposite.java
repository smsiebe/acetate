package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Optional annotation used to describe t domain model meta on a composite
 * containing multiple values.
 * <p>
 * To describe a composite which is not multi-valued, use the {@link Composite}
 * annotation.
 *
 * @see Composite
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@Documented
public @interface MultiComposite {

    /**
     * OPTIONAL alternate, container-model unique, name to use for the composite
     * collection.
     *
     * By default, the model name of the composite model is the method name of
     * the {@link Accessor} method.
     *
     * @return container-model unique composite collection name
     */
    String name() default "";

    /**
     * OPTIONAL alternate name used for an item of the composite.
     * <p>
     * By default, the name of the item is the same as the composite model name.
     *
     * @return composite collection item name
     */
    String itemName() default "";

    /**
     * OPTIONAL display name of the model; unspecified the display name is the
     * same as the {@link #name() } of the model.
     *
     * @return display name of the model
     */
    String displayName() default "";
}
