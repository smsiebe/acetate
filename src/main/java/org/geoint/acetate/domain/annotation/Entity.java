package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies a {@link EntityModel} model within the domain.
 *
 * @see EntityModel
 */
@Documented
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface Entity {

    /**
     * OPTIONAL domain-unique "short" name of the model; unspecified the name is
     * derived from the domain and type name.
     *
     * @return name of the model
     */
    String name() default "";

}
