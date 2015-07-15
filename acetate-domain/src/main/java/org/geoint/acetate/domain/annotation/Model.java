package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.model.common.Version;

/**
 * Annotation used to declare a Java class as a model of the domain model.
 * <p>
 * Most domain models will use alternative annotations to identify its
 * specialized model type, such as an {@link Entity} or {@link Service}. When
 * these alternative annotations are used, this annotation does not need to be
 * present, and will be overridden by the specialized model definition. In
 * practice, this annotation is normally only used to define abstract/parent
 * models or models only used as a composite.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    /**
     * Name of the domain that defines this model.
     *
     * @return domain model name
     */
    String domain();

    /**
     * OPTIONAL domain-unique "short" name of the model; unspecified the name is
     * derived from the domain and type name.
     *
     * @return name of the model
     */
    String name() default "";

    /**
     * OPTIONAL display name of the model; unspecified the display name is the
     * same as the {@link #name() } of the model.
     *
     * @return display name of the model
     */
    String displayName() default "";

    /**
     * OPTIONAL version this model was first added to the domain; unspecified
     * indicates the model was present since the first version of the domain.
     *
     * @see Version
     * @return domain model version
     */
    String version() default "";

}
