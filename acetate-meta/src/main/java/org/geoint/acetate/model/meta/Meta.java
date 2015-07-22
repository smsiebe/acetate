package org.geoint.acetate.model.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.ModelRegistry;

/**
 * Identifies the annotation this annotates as defining a component of a model.
 * <p>
 * Elements annotated with model annotations are registered with, and
 * discoverable through, the {@link ModelRegistry}.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Meta {

}
