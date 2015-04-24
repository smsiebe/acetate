package org.geoint.acetate.model.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.AcetateDataAttribute;

/**
 * Identifies a data component as the globally unique identifier for the data
 * item instance. All root data instances <b>MUST</b> have GUID; aggregates
 * <b>SHOULD</b> have a GUID.
 *
 * @see AcetateDataAttribute.GUID
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GUID {

}
