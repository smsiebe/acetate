package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.DomainModel;

/**
 * Defines the domain model for the model object.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Domain {

    /**
     * Name of the domain model.
     *
     * @return unique name of the domain model
     */
    String name();

    /**
     * Domain model version this object is associated.
     *
     * @return domain model version
     */
    long version();
}
