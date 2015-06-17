
package org.geoint.acetate.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.bind.transform.ObjectConverter;

/**
 * Specifies a {@link ObjectConverter} to use to convert the annotated object 
 * during binding operations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
public @interface Converter {

    /**
     * Converter to use for this object.
     * 
     * @return converter to use during binding operations
     */
    Class<? extends ObjectConverter<?,?>> value();
}
