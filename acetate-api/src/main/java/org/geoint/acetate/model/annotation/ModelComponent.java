package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.codec.BinaryCodec;

/**
 * Use to augment the model of a individual model component.
 *
 * This annotation may be used directly on a component method or defined within
 * a {@link DomainModel}.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelComponent {

    /**
     * Relative path of the model component to change.
     *
     * The default path is derived from the java package, class, and method
     * name.
     *
     * @return relative component path
     */
    String path() default "";

    /**
     * Optional listing of model component aliases.
     *
     * @return optional aliases, empty array means no aliases.
     */
    String[] alias() default {};

    /**
     * Optional data constraints added to the model component.
     *
     * @return data constraints for this model
     */
    Class<? extends ComponentConstraint>[] constraints() default {};

    /**
     * Optional explicitly defined chain of binary converter for this model
     * component.
     *
     *
     * @return optional binary converter chain
     */
    Class<? extends BinaryCodec>[] codec() default {};

}
