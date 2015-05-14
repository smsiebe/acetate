package org.geoint.acetate.model.annotation.constraint;

import gov.ic.geoint.acetate.bind.AnnotationDataFactory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Identifies an annotation used to define a {@link ComponentConstraint} for a
 * model component.
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {

    /**
     * The factory use to instantiate the ComponentConstraint instance.
     *
     * @return component constraint factory
     */
    Class<? extends AnnotationDataFactory<? extends ComponentConstraint>> constructor();
}
