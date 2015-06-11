package org.geoint.acetate.model.attribute;

import gov.ic.geoint.acetate.bind.Factory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.EntityModel;

/**
 * "Marker" attribute which identifiers the component of an
 * {@link EntityModel} as the {@link EntityVersion instance version
 * field}.
 *
 * @see Version
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Factory(type = EntityVersion.class)
public @interface Version {

}
