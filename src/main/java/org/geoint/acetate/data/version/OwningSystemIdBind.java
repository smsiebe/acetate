package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a field that may contain a external system unique identity of the
 * data item.
 * <p>
 * The annotated value must only be system-unique, not globally unique.
 *
 * @see OwningSystem
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OwningSystemIdBind {

}
