package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which identifies a metamodel annotation.
 * 
 * Classes that are annotated with any metamodel annotation will be processed 
 * as a metamodel component.  Upon completion of the metamodel generation, 
 * the metamodels will be provided to {@link MetaModeler} implementations.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Meta {

}
