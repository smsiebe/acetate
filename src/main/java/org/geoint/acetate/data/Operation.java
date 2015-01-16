package org.geoint.acetate.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Identifies a method as an operation and not one that provides data for 
 * binding purposes.
 *
 * An Operation will not be data bound.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Operation {

}
