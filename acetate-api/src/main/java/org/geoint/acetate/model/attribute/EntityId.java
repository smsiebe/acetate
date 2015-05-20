package org.geoint.acetate.model.attribute;

import gov.ic.geoint.acetate.bind.DataContextAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies the domain object at an EntityObject by identifying one of its
 * components as an entity id.
 *
 * All root data instances <b>MUST</b> have an entity id; aggregates
 * <b>SHOULD</b> have a entity id.
 *
 * @see EntityGuid
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Attribute(
        @DataContextAnnotation(type = EntityGuid.class))
public @interface EntityId {

}
