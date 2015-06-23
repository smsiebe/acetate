package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.meta.model.ObjectModel;

/**
 * Annotation which identifies annotation as one that defines metamodel
 * behavior.
 *
 * A metamodel attribute may define {@link MetaAttribute attributes}, the value
 * of which can be retrieved from {@link ObjectModel#getAttributes() }.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Meta {

    /**
     * Metamodel name, providing context for the meta attributes.
     *
     * A metamodel may define zero or more metamodel annotations; metamodel
     * annotations must share the same model name.
     *
     * @return name of the metamodel this meta annotation is associated
     */
    String name();

    /**
     * Metamodel version.
     *
     * @return version of the metamodel
     */
    String version();

}
