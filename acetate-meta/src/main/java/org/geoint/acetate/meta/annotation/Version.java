package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Defines the version of the object model (not the version of the class).
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Meta
public @interface Version {

    /**
     * {@link MetaVersion#asString() String-formatted} version of the object
     * version.
     *
     * @return object model version
     */
    String value();

}
