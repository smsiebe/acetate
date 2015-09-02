package org.geoint.acetate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.DomainMetaModel;
import org.geoint.metamodel.annotation.ModelAttribute;
import org.geoint.metamodel.annotation.Model;

/**
 * Identifies the type as a domain model entity.
 *
 * @see EntityModel
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Entity {

    /**
     * OPTIONAL Name of the domain that defines this model.
     * <p>
     * If not explicitly set a domain type will inherit the domain name defined
     * on the package (if set) or will use the default domain name.
     *
     * @return domain model name
     */
    @ModelAttribute(name = DomainMetaModel.DOMAIN_NAME)
    String domain() default "";

    /**
     * OPTIONAL Domain model version of the data type.
     * <p>
     * If not explicitly set a domain type will inherit the version defined on
     * the package (if set) or will use the default domain version.
     *
     * @return
     */
    @ModelAttribute(name = DomainMetaModel.DOMAIN_VERSION)
    String domainVersion() default "";

}
