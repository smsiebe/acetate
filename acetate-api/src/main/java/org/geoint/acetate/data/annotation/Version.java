package org.geoint.acetate.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.annotation.Model;

/**
 * Identifies a field as the model instance version field.
 *
 * <p>
 * A data model <b>SHOULD</b> specify one (and only one) version field that is
 * unique for each state change of the model instance. The value of the version
 * field must be instance/version but the version field itself need not be
 * globally unique (think of the data instance GUID as a namespace). Data
 * instance version identifies <b>MUST NOT</b> be reused by a data instance.
 *
 * <p>
 * If a data model specifies a Version, the field must never be null.
 *
 * <p>
 * Version fields are inherited and only the most concrete field will be used as
 * the models version field.
 *
 * <p>
 * The field annotated with Version must be, or be convertible to, a UTF-8
 * string. The following field values are supported for Version:
 * <ul>
 * <li>an instance of java.lang.String, renderable with the UTF-8 charset</li>
 * <li>can be converted using {@link  String#valueOf(Object) } and not return the
 * value {@code null}</li>
 * <li>specify a {@link DataConverter}</li>
 * </ul>
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Version {

}
