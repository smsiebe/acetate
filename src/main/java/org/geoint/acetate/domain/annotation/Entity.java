package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.metamodel.DomainMetaModel;
import org.geoint.metamodel.MetaAttribute;
import org.geoint.metamodel.MetaModel;

/**
 * Identifies the type as a domain model entity.
 *
 * @see EntityModel
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MetaModel
public @interface Entity {

    /**
     * Name of the domain that defines this model.
     *
     * @return domain model name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_NAME)
    String domain();

    /**
     * Domain model version of the data type.
     *
     * @return
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_VERSION)
    String domainVersion();

    /**
     * OPTIONAL Domain type name.
     *
     * @return domain type name
     */
    @MetaAttribute(name = DomainMetaModel.DOMAIN_TYPE_NAME)
    String name();

}
