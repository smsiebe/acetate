package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.DomainModel;

/**
 * Declares a class as a domain model component.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Domains.class)
public @interface Model {

    /**
     * Domain model unique component name.
     *
     * @return unique name of the domain model component
     */
    String name();

    /**
     * Name of the domain model this component belongs to.
     *
     * @return domain model name
     */
    String domainName();

    /**
     * Domain model version this object is associated.
     *
     * @return domain model version
     */
    long domainVersion();
}
