package org.geoint.acetate.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Annotates the data item-unique version field.
 *
 * The value of the version field must be unique (to the data item) each time
 * the state of the data item changes. However, the version may also change even
 * if the data item does not appear to have a state change to the client.
 *
 * The expected data type annotated by version is a String, or is convertible to
 * String, through the use of {@link String#valueOf(java.lang.Object) }
 * method.
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Version {

}
