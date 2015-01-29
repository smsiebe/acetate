package org.geoint.acetate.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Optional annotation identifying a method as one that provide field data -
 * optional because this is assumed by default.
 *
 * The Field annotation can be used to either explicity indicate that the method
 * is used as a field by acetate (ie documentation purposes) or to override how
 * the field is handled by acetate (see the optional annotation attributes).
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Field {

    /**
     * Aliases for this field, overriding the name derived by acetate.
     *
     * Alias allow the field to be bound to any of the aliases for the field. By
     * defining aliases acetate will not generate a field name, as defined in
     * {@link DataField#getName()}. If you wish to also have the default field
     * name, add it to the aliases.
     *
     * @return names that can be used to reference the field
     */
    String[] aliases() default {};

}
