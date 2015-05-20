package org.geoint.acetate.model.constraint;

import gov.ic.geoint.acetate.bind.DataContextAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies, and provides creational context for, an annotation which defines
 * a component constraint.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {

    DataContextAnnotation value();
}
