package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.DomainModel;

/**
 * Declares a class as a component of a domain model.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Domains.class)
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
