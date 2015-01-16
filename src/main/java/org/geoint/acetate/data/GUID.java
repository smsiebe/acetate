package org.geoint.acetate.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Identifies a data item field as the globally unique identifier for the data
 * item instance.
 *
 * For simplicity, all data items serialized by acetate must have GUID assigned
 * to it. We could have make exceptional cases, but that increases what you need
 * to know about the framework. Simply put - if you have an Object, make sure
 * somewhere in its inheritance chain there is a field annotated with GUID.
 *
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface GUID {

}
