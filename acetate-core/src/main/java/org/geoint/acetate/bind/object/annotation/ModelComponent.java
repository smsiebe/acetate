package org.geoint.acetate.bind.object.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used within {@link Model} to modify the data model through annotations.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelComponent {

    /**
     * Relative path of the model component to change.
     *
     * @return relative component path
     */
    String path();

    /**
     * Component model settings.
     *
     * @return component model settings
     */
    Model model();

}
