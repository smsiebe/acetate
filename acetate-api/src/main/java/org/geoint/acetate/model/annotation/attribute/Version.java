package org.geoint.acetate.model.annotation.attribute;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.annotation.attribute.Attribute;
import org.geoint.acetate.model.attribute.AcetateDataAttribute.AcetateComponentAttributeConstructor;

/**
 * Identifies a field as the model instance version field.
 *
 * @see AcetateDataAttribute.VERSION
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Attribute(constructor = AcetateComponentAttributeConstructor.class)
public @interface Version {

}
