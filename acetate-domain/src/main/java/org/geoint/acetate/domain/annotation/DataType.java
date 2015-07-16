package org.geoint.acetate.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;
import org.geoint.acetate.data.DataCodec;
import org.geoint.acetate.model.common.Version;

/**
 * Annotates a java class defining a domain data type.
 *
 * A domain data type is one which is represented by a single value when
 * serialized.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataType {

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
     * OPTIONAL default data codec class if the annotated class does not
     * implement the {@link DataCodec} interfaces.
     *
     * @return data type codecs
     */
    Class<? extends DataCodec> defaultCodec() default DataCodec.class;

    /**
     * OPTIONAL default comparator class name if the class does not implement
     * {@link Comparable}.
     *
     * @return default comparator domain name
     */
    Class<? extends Comparator> defaultComparator() default Comparator.class;

}
