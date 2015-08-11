package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.DomainMetaModel;
import org.geoint.metamodel.annotation.MetaAttribute;
import org.geoint.metamodel.annotation.MetaModel;

/**
 * Annotation used to declare a basic domain type as part of a domain model.
 */
@Documented
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface Domain {

    /**
     * OPTIONAL Name of the domain that defines this model.
     * <p>
     * If not explicitly set a domain type will inherit the domain name defined
     * on the package (if set) or will use the default domain name.
     *
     * @return domain model name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_NAME)
    String domain() default "";

    /**
     * OPTIONAL Domain model version of the data type.
     * <p>
     * If not explicitly set a domain type will inherit the version defined on
     * the package (if set) or will use the default domain version.
     *
     * @return
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_VERSION)
    String domainVersion() default "";

    /**
     * OPTIONAL Domain type name.
     * <p>
     * Setting this attribute at the package level will have no effect.
     *
     * @return domain type name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_TYPE_NAME)
    String name() default "";

}
