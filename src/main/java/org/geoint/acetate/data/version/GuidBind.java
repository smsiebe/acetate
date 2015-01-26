package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a publicly accessible data element as the globally unique
 * identifier of the data type.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuidBind {

    /**
     * Optional data element name override.
     *
     * The default data element name is guaranteed to be unique for the data
     * type.
     *
     * @return override name for the data element
     */
    String name() default "";
}
