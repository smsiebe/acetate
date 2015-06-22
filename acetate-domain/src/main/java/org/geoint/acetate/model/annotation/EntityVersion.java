package org.geoint.acetate.model.annotation;

import org.geoint.acetate.bind.Factory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.attribute.EntityVersionAttribute;

/**
 * Identifies the Entity version component.
 *
 * @see EntityVersion
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Factory(type = EntityVersionAttribute.class)
public @interface EntityVersion {

}
