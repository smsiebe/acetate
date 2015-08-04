package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies the {@link Accessor accessor} method which provides the unique ID
 * of the domain entity.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface EntityId {

}
