package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a data element as one that records the creator of the data.
 * <p>
 The CreatorBind element allows components to reflectively realize the
 significance of this field. For example, a terpene-persistence service
 implementation may use this field annotation to ensure that the value never
 changes, or a terpene-event service implementation to ensure that the creator
 of an event is the same as the current user context (to ensure there isn't
 any attempts to nefariously associate changes to other users).
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatorBind {

}
