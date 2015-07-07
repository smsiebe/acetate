package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Annotation which, when annotated on an ANNOTATION_TYPE, identifies it as one
 * which is used to a component of its metamodel.
 * <p>
 * A MetaModel annotation may contain attributes which are annotated by
 * {@link MetaOperation} annotations, identifying the attribute as providing the
 * metamodel value. Typical MetaOperations are define the domain of the
 * metamodel (ie {@link DomainName}, {@link DomainVersion}), so that developers
 * only need to annotate with the framework-defined metamodel annotation (not
 * needing the {@link Model} annotation). Metamodel implementation may define
 * their own meta operations as well.
 * <p>
 * MetaModel itself is a metamodel annotation using the <i>acetate.*</i> prefix
 * for its annotations. As such, the <i>acetate.*</i> metamodel attribute prefix
 * is reserved for use for the acetate metamodel framework.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@MetaModel(name = DomainModel.ACETATE_DOMAIN_NAME,
        version = DomainModel.ACETATE_DOMAIN_VERSION)
public @interface MetaModel {

    /**
     * Metamodel domain name, providing context for the meta attributes.
     *
     * A metamodel may define zero or more metamodel annotations; metamodel
     * annotations must share the same model name.
     *
     * @return name of the metamodel this meta annotation is associated
     */
    @DomainName
    String name();

    /**
     * The metamodel version this annotation is associated with.
     *
     * @see MetaVersion
     * @return version of the metamodel, which may be a specific version or a
     * VersionRange
     */
    @DomainVersion
    String version();

    /**
     * Identifies zero or more operations, identified by being annotated by a
     * {@link MetaOperation} annotation, that must be present on the metamodel
     * component.
     */
    Class<? extends Annotation> requiredOperations() default new [0];
}
