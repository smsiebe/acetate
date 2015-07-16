package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import org.geoint.acetate.domain.model.ConstraintModel;
import org.geoint.acetate.model.common.Version;

/**
 * Annotation added to a Bean Validation custom validator annotation definition
 * to define its domain model properties.
 * <p>
 * The DataConstraint is simply added to the annotation type definition and does
 * not replace the {@link javax.validation.Constraint} annotation required by
 * the Bean Validation specification.
 *
 * @see Constraint
 * @see ConstraintModel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DataConstraint {

    /**
     * Name of the domain that defines this model.
     *
     * @return domain model name
     */
    String domain();

    /**
     * OPTIONAL domain-unique "short" name of the constraint; unspecified the
     * name is derived from the domain and type name.
     *
     * @return name of the model
     */
    String name() default "";

    /**
     * OPTIONAL display name of the model; unspecified the display name is the
     * same as the {@link #name() } of the model.
     *
     * @return display name of the model
     */
    String displayName() default "";

    /**
     * OPTIONAL version this model was first added to the domain; unspecified
     * indicates the model was present since the first version of the domain.
     *
     * @see Version
     * @return domain model version
     */
    String version() default "";
}
