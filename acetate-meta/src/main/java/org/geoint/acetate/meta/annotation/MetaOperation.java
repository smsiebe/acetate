package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;

/**
 * Used to identify an annotation as one which defines a metamodel operation.
 * <p>
 * An annotation defined as a MetaOperation can be used to define attributes of
 * a {@link MetaObject} annotation as one which provides metamodel information,
 * may be {@link MetaModel#requiredOperations() used to declare required
 * operations for a metamodel component}, or used to annotate an operation on a
 * metamodel component (annotated with {@link MetaObject} to identify the
 operation (such as meeting the MetaObject#requiredOperations requirement).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@MetaObject(name = DomainModel.ACETATE_DOMAIN_NAME,
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface MetaOperation {

    @DomainName
    String domainName();

    @DomainVersion
    String domainVersion();
}
