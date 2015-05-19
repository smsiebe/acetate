package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.annotation.attribute.Attribute;
import org.geoint.acetate.model.annotation.constraint.Constraint;

/**
 * Use to augment the model of a individual model component.
 *
 * This annotation may be used directly on a component method or defined within
 * a {@link DomainModel}.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentView {

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
     * Optional data constraints added to the model component.
     *
     * @return data constraints for this model
     */
    Constraint[] constraints() default {};

    /**
     * Optional data attributes added to the model component.
     *
     * @return attributes for this model
     */
    Attribute[] attributes() default {};

    /**
     * Optional binary codec to use for this component, overriding the default
     * codec defined by the domain model.
     *
     * @return optional binary codec which overrides the domain default
     */
    Class<BinaryCodec> binaryCodec() default BinaryCodec.class;

    /**
     * Optional character codec to use for this component, overridding the
     * default codec defined by the domain model.
     *
     * @return optional character codec which overrides the domain default
     */
    Class<CharacterCodec> charCodec() default CharacterCodec.class;

}
