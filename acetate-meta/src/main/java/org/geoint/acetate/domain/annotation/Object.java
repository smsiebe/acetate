package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.meta.annotation.Meta;
import org.geoint.acetate.meta.annotation.MetaModel;

/**
 * Annotation used to declare a Java class as a basic {@link ObjectModel
 * domain model object} that will be included in the specified domain model.
 *
 * Most frameworks will define their own metamodels, often defining their own
 * {@link MetaModel annotations} which their frameworks can use to easily
 * discover relevant components.
 *
 * @see DomainModel
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel(name = DomainModel.ACETATE_DOMAIN_NAME,
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface Object {

    /**
     * Object model unique component name.
     *
     * @return unique name of the domain model component
     */
    @Meta(name = ObjectModel.META_OBJECT_NAME)
    String name();

    /**
     * Name of the domain model this component belongs to.
     *
     * @return domain model name
     */
    @Meta(name = DomainModel.META_DOMAIN_NAME)
    String domainName();

    /**
     * Object model version this object is associated.
     *
     * @see MetaVersion
     * @return domain model version
     */
    @Meta(name = DomainModel.META_DOMAIN_VERSION)
    String domainVersion();

}
