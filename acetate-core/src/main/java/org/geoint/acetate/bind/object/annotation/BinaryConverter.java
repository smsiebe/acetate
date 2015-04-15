package org.geoint.acetate.bind.object.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.transform.DataConverter;

/**
 * Defines an alternative default binary data formatter for this component.
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BinaryConverter {

    /**
     * Formatter to use to create the alternative default data format for this
     * field.
     *
     * @return binary formatter to use to create the alternative default binary
     * representation of the field
     */
    Class<? extends DataConverter<?, byte[]>> value();
}
