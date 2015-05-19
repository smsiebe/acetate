package org.geoint.acetate.model.annotation.attribute;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.attribute.AcetateDataAttribute;
import org.geoint.acetate.model.attribute.AcetateDataAttribute.AcetateComponentAttributeConstructor;

/**
 * Identifies the domain object at an EntityObject by identifying one of its 
 * components as an entity id.
 *
 * All root data instances <b>MUST</b> have EntityId; aggregates
 <b>SHOULD</b> have a EntityId.
 *
 * @see AcetateDataAttribute.GUID
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Attribute(constructor = AcetateComponentAttributeConstructor.class)
public @interface EntityId {

}
