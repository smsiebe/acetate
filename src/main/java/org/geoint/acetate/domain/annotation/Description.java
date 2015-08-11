package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.annotation.MetaModel;

/**
 * Used to provide an optional model description within code.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@MetaModel
public @interface Description {

    /**
     * Model component description;
     *
     * @return model description
     */
    String value();
}
