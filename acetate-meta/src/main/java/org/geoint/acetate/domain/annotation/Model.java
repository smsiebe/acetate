package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.annotation.DomainName;
import org.geoint.acetate.meta.annotation.DomainVersion;
import org.geoint.acetate.meta.annotation.MetaModel;
import org.geoint.acetate.meta.annotation.ModelName;

/**
 * Annotation used to declare a Java class (or all classes within a package)
 * component(s) of the specified domain model.
 *
 * Most frameworks will define their own metamodels, often defining their own
 * {@link MetaModel annotations} which their frameworks can use to easily
 * discover relevant components.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel(name = DomainModel.ACETATE_DOMAIN_NAME,
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface Model {

    /**
     * Domain model unique object model name.
     *
     * @return unique name of the domain model component
     */
    @ModelName
    String name() default "";

    /**
     * Name of the domain model this component belongs to.
     *
     * @return domain model name
     */
    @DomainName
    String domainName();

    /**
     * Model model version this object is associated.
     *
     * @see MetaVersion
     * @return domain model version
     */
    @DomainVersion
    String domainVersion();

}
