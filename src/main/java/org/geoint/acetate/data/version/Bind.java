package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.terpene.data.codec.DataCodec;

/**
 * Overrides the derived data binding values for a data element.
 * <p>
 * By default any declared method that returns a value is considered a "data
 * element" which will be bound. This annotation customizes the way that data is
 * bound.
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {

    /**
     * Optional overridden name of the bound data element.
     * <p>
     * If a data element is bound with an alternative name this new name must be
     * used in all binding situations.
     *
     * @return overriding data element name or empty string if not overridden
     */
    String name() default "";

    /**
     * Optional converter that is used to convert a data element to/from an
     * alternative data element.
     *
     * @return
     */
    Class<? extends DataCodec> converter() default DataCodec.class;
}
