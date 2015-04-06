package org.geoint.acetate.bind.object;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.bind.spi.BinaryDataFormatter;
import org.geoint.acetate.model.annotation.Model;

/**
 * Defines an alternative default binary data formatter for this component.
 *
 * @see BinaryDataFormatter
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface DefaultBinaryFormatter {

    /**
     * Formatter to use as the default binary formatter for this data model 
     * component.
     *
     * @return default binary formatter for this model component
     */
    Class<? extends BinaryDataFormatter> value();
}
