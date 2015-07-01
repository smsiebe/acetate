package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.OperationModel;
import org.geoint.acetate.meta.annotation.Meta;
import org.geoint.acetate.meta.annotation.MetaModel;

/**
 * Optionally annotates a models method, overridding default values.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetaModel(name = "acetate", version = "1.0-BETA")
public @interface Operation {

    /**
     * Overrides the operation name, which is normally the method name.
     *
     * @return explicit operation name
     */
    @Meta(name = OperationModel.META_OPERATION_NAME)
    String name() default "";

    /**
     * Overrides the operations description.
     *
     * @return explicit operation description
     */
    @Meta(name = OperationModel.META_OPERATION_DESCRIPTION)
    String description() default "";

}
