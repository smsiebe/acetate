package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Identifies the method returning the version of the domain model.
 * <p>
 * The operation must return either an instance of {@link MetaVersion} or a
 * String which is formatted as defined by {@link MetaVersion#asString()}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetaOperation(domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface DomainVersion {

}
