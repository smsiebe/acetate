package org.geoint.acetate.entity.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.meta.annotation.Meta;

/**
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

    @Meta(name = ObjectModel.META_OBJECT_NAME)
    String entityName() default "";
    
    @Meta(name = DomainModel.ACETATE_DOMAIN_NAME)
    String domainName();

    @Meta(name = DomainModel.ACETATE_DOMAIN_VERSION)
    String domainVersion();
}
