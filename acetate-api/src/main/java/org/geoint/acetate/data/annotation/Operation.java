package org.geoint.acetate.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.annotation.Model;

/**
 * Identifies a method as an operation, and not one that provides a field.
 * <p>
 * Methods with this annotation will be ignored by acetate for binding purposes.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Operation {

}
