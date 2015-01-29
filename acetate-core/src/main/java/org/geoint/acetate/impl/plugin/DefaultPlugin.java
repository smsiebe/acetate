package org.geoint.acetate.impl.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates the default acetate plugin for a given interface type.
 *
 * This annotation can only be used on one subclass of a plugin interface;
 * acetate extention libraries must not annotate classes with this or a
 * {@link CannotResolvePluginException} runtime exception will be thrown.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultPlugin {

}
