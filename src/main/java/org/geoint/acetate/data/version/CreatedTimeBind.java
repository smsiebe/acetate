package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZonedDateTime;

/**
 * Identifies a data element that records the creation time of the data.
 * <p>
 The CreatedTimeBind element allows components to reflectively realize the
 significance of this field. For example, a terpene-persistence service
 implementation may use this field annotation to ensure that the value never
 changes.
 <p>
 * This may annotate the follow data types:
 * <ul>
 * <li>{@link ZonedDateTime}</li>
 * </ul>
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatedTimeBind {

}
