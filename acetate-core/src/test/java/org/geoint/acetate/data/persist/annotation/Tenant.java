package org.geoint.acetate.data.persist.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.annotation.Model;

/**
 * Annotates a field that identifies the tenant owner of the data.
 * 
 * The data type may be a int, long, or String, or an Object which has a 
 * {@link GUID} field.
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Tenant {

}
