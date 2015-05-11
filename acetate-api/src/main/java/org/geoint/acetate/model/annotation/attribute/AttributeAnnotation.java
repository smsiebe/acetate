package org.geoint.acetate.model.annotation.attribute;

import gov.ic.geoint.acetate.bind.AnnotationDataFactory;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 * Identifies an annotation used to define a {@link ComponentAttribute} for a
 * model component.
 *
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeAnnotation {

    /**
     * The factory use to instantiate the ComponentAttribute instance.
     *
     * @return
     */
    Class<? extends AnnotationDataFactory<? extends ComponentAttribute>> constructor();
}
