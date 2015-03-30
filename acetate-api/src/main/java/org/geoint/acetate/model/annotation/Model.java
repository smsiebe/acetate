package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies an annotation that defines/identifies characteristics of the data
 * model.
 *
 * This annotation is not only designed to be used internally by acetate but can
 * be used by external frameworks that make use of or extend the acetate data
 * model. Acetate maintains an in-memory index of the data model, indexes all
 * annotations which are annotated by this annotations, and provides an
 * interface to query on the model for these annotations. Developers should be
 * mindful, however, that there is a cost (memory and lookup time) when adding
 * annotations - so use it...but be gentle.
 *
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

}
