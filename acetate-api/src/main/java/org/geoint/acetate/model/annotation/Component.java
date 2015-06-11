package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.ModelAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Use to augment the model of a individual model component.
 *
 * This annotation may be used directly on a component method or defined within
 * a {@link DomainModel}.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * {@link ModelAddress} URI of the model component to change.
     *
     * @return component address
     */
    String address();

    /**
     * Optional - Overrides the data constraints of the component.
     *
     * When no constraints defined the constraints defined by the model are
     * used. If no constraints are desired, explictly define an empty array.
     *
     * @return data constraints for this model
     */
    Class<? extends ComponentConstraint>[] constraints() default {
        UseModelConstraints.class
    };

    /**
     * Optional - Overrides the data attributes of the component.
     *
     * When no attributes are defined, the attributes defined by the model are
     * used. If no attributes are desired, explictly define an empty array.
     *
     * @return attributes for this model
     */
    Class<? extends ComponentAttribute>[] attributes() default {
        UseModelAttributes.class
    };

    /**
     * Optional - binary codec to use for this component, overriding the default
     * codec defined by the domain model.
     *
     * @return optional binary codec which overrides the domain default
     */
    Class<BinaryCodec> binaryCodec() default BinaryCodec.class;

    /**
     * Optional - character codec to use for this component, overridding the
     * default codec defined by the domain model.
     *
     * @return optional character codec which overrides the domain default
     */
    Class<CharacterCodec> charCodec() default CharacterCodec.class;

    /**
     * "Magic" marker constraint used to indicate that the component constraints
     * defined by the model should be used.
     *
     * The UseModelConstraints constraint will be replaced by those defined by
     * the data model.
     */
    public final class UseModelConstraints implements ComponentConstraint {

        @Override
        public <T> boolean validate(ObjectModel<T> model, T data) {
            return true;
        }

    }

    /**
     * "Magic" marker attribute used to indicate that the component attributes
     * defined on the data model should be used.
     *
     * The UseModelAttributes attribute will be replaced by those defined by the
     * data model.
     */
    public final class UseModelAttributes implements ComponentAttribute {

    }
}
