package org.geoint.acetate.bind.object;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.bind.spi.StringDataFormatter;
import org.geoint.acetate.model.annotation.Model;

/**
 * Defines an alternative default String data format for the data type.
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface DefaultStringFormat {

    Class<? extends StringDataFormatter> value();
}
