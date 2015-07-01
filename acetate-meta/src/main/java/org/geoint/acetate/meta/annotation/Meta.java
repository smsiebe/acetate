package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.ObjectModel;

/**
 * Used to define a metamodel attribute on a {@link Meta meta annotation}.
 *
 * Metamodel attributes may be retrieved available from the
 * {@link ObjectModel#getAttributes()} method.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Meta {

    /**
     * Overrides the meta attribute name.
     *
     * The default name for a meta attribute is in the format:
     * <i>[metaModelName].[annotationAttributeName]</i>
     *
     * @return overriding meta attribute name
     */
    String name() default "";

}
