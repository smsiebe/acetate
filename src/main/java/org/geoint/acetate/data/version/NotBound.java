
package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method that returns a value as one that should not be data bound.
 * <p>
 * By default all methods that return a value on a Java object is considered a 
 * "data element" and will be bound.  By annotating the method with NotBound the 
 * data returned from the method will not be considered a data element.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NotBound {

}
