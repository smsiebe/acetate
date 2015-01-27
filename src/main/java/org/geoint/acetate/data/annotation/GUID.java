package org.geoint.acetate.data.annotation;

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
 * All data items serialized by acetate <b>SHOULD</b> have GUID assigned to it.
 * Having a GUID allows the various formatters to bind much more cleanly.
 *
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface GUID {

}
