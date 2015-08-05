package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies the method as an {@link Accessor accessor} method which provides
 * the unique ID of the domain entity.
 * <p>
 * Annotating a method as an EntityId infers it is an {@link Accessor}, so it
 * does not need to be explicitly added.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface EntityId {

    /**
     * OPTIONAL model unique name of the data type being accessed.
     *
     * By default, the name of the data is the simple name of the method.
     *
     * @return container-model unique accessor name
     */
    String name() default "";

}
