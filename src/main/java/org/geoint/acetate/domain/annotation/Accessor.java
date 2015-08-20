package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.annotation.Model;

/**
 * Identifies a method as providing idempotent, safe, access to data from a
 * composite model.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
@Model
public @interface Accessor {

    /**
     * OPTIONAL model unique name of the data type being accessed.
     *
     * By default, the name of the data is the simple name of the method.
     *
     * @return container-model unique accessor name
     */
    String name() default "";

}
