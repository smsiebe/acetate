package org.geoint.acetate.data.persist.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZonedDateTime;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Annotates a {@link ZonedDateTime} field that identifies the last time the 
 * data was modified.
 * 
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Modified {

}
