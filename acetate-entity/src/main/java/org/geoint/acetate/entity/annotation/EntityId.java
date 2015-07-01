package org.geoint.acetate.entity.annotation;

import org.geoint.acetate.bind.Factory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.entity.attic.attribute.EntityIdAttribute;

/**
 * Identifies the entity id component of an Entity.
 *
 * All root data instances <b>MUST</b> have an entity id; aggregates
 * <b>SHOULD</b> have a entity id.
 *
 * @see EntityIdAttribute
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Factory(type = EntityIdAttribute.class)
public @interface EntityId {

}
