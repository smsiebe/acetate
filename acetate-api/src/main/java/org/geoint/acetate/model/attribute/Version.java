package org.geoint.acetate.model.attribute;

import gov.ic.geoint.acetate.bind.DataContextAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a field as the model instance version field.
 *
 * @see Version
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Attribute(
        @DataContextAnnotation(type = EntityVersion.class))
public @interface Version {

}
