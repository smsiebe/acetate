package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.annotation.MetaObject;
import org.geoint.acetate.meta.annotation.ModelName;

/**
 * Optionally annotates a models method, overridding default values.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetaObject(name = DomainModel.ACETATE_DOMAIN_NAME,
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface Operation {

    /**
     * Overrides the operation name, which is normally the method name.
     *
     * @return explicit operation name
     */
    @ModelName
    String name() default "";

    /**
     * Overrides the operations description.
     *
     * @return explicit operation description
     */
    String description() default "";

}
