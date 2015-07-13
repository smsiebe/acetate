package org.geoint.acetate.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelVersion;

/**
 * Annotation used to declare a Java class (or all classes within a package)
 * component(s) of the specified domain model.
 *
 * Most frameworks will define their own metamodels, often defining their own
 * {@link MetaObject annotations} which their frameworks can use to easily
 * discover relevant components.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Domain {

    /**
     * Domain model name.
     *
     * @return domain model name
     */
    String name();

    /**
     * Domain model version this object is associated.
     *
     * @see ModelVersion
     * @return domain model version
     */
    String version();

}
