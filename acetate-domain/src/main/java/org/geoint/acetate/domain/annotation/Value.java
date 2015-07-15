package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.domain.BinaryCodec;
import org.geoint.acetate.model.common.Version;

/**
 *
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {

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

    /**
     * The <i>default</i> binary codec to use for this data type.
     *
     * @return binary data codec for this value object
     */
    Class<? extends BinaryCodec<?>> codec();

}
