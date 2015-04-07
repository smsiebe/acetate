package org.geoint.acetate.bind.object.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.data.DataFormatter;

/**
 * Defines an alternative String data format to use by default for this field.
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface StringFormatter {

    /**
     * String formatter to use for this field.
     *
     * @return formatter to produce the default data format for the field
     */
    Class<? extends DataFormatter<String>> value();
}
