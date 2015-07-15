package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate that the type/method should not be included in
 * modeling.
 * <p>
 * <h1>Use</h1>
 * The DoNotModel annotation can be used on class/interface (TYPE) definitions 
 * as well as methods that might otherwise be "assumed" part of the domain.  
 * Normally, however, only components explictly annotated as part of a domain 
 * model will be included in a domain model.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DoNotModel {

}
