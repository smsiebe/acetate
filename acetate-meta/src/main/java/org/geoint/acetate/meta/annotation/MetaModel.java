package org.geoint.acetate.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Annotation which identifies annotation as one that defines a specialized
 * component of the specified metamodel, specified by the name and version of
 * this annotation.
 * <p>
 * A annotation annotated with MetaModel is checked for any annotation
 * attributes/methods annotated with {@link Meta}. If there are any, the value
 * of these attributes will be accessible from
 * {@link ObjectModel#getAttributes()}. This is particularly powerful in that
 * frameworks extending the metamodel can augment/replace the acetate-defined
 * metamodel annotations (for example {@link Object}) simply by defining their
 * own metamodel annotation and including the required metamodel attributes by
 * name:
 * <ul>
 * <li>{@link DomainModel#META_DOMAIN_NAME}</li>
 * <li>{@link DomainModel#META_DOMAIN_VERSION</li>
 * <li>{@link ObjectModel#META_OBJECT_NAME</li>
 * </ul>
 * By the metamodel including these annotations in its own custom metamodel
 * annotation, developers do not need to use both the custom metamodel
 * annotation and the acetate-defined annotations...they may not even know the
 * annotations are processed by acetate at all.
 * <p>
 * MetaModel itself is a metamodel annotation using the <i>acetate.*</i> prefix
 * for its annotations. As such, the <i>acetate.*</i> metamodel attribute prefix
 * is reserved for use for the acetate metamodel framework.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@MetaModel(name = "acetate", version = "1.0-BETA")
public @interface MetaModel {

    /**
     * Metamodel domain name, providing context for the meta attributes.
     *
     * A metamodel may define zero or more metamodel annotations; metamodel
     * annotations must share the same model name.
     *
     * @return name of the metamodel this meta annotation is associated
     */
    @Meta(name = "acetate.metamodel.name")
    String name();

    /**
     * The metamodel version this annotation is associated with.
     *
     * @see MetaVersion
     * @return version of the metamodel, which may be a specific version or a
     * VersionRange
     */
    @Meta(name = "acetate.metamodel.version")
    String version();

}
