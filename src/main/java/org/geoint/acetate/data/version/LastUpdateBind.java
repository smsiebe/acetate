package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated on a data retrieval method to identify a field that represents the
 * last updated time of the data.
 * <p>
 * The last update time of the data cannot be used as an alternative to the
 * {@link DateVersion} field, as the last update time may not accurately reflect
 * a new version of the data.
 * <p>
 * This may annotate the follow data types:
 * <ul>
 * <li>{@link ZonedDateTime}</li>
 * </ul>
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastUpdateBind {

}
