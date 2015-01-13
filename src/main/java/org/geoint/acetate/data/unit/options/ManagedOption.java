package org.geoint.acetate.data.unit.options;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.data.unit.options.OptionsManager;

/**
 * Indicates that a data field value, when set, will be equal to one of the
 * enumerative values that is managed by the {@link OptionsManager} for the
 * specified option type.
 *
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagedOption {

    /**
     * The option type name.
     *
     * The option type name is used by the OptionsManager to manage the options
     * that are acceptable for this given type.
     *
     * @return the option type name
     */
    String value();

}
