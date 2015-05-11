package org.geoint.acetate.model.annotation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.constraint.NotNullConstraint.NotNullConstraintAnnotationConstructor;

/**
 * Annotates a model component method, indicating that a (valid) component
 * instance/value is required for a valid data instance.
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ConstraintAnnotation(constructor = NotNullConstraintAnnotationConstructor.class)
public @interface NotNull {

}
