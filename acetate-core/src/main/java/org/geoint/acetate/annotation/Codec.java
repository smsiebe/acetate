package org.geoint.acetate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.data.DataCodec;

/**
 * Defines a {@link DataCodec codec} to use to write the domain data as bytes or
 * String.
 * <p>
 * When annotated on a TYPE this declares the default codec to use for that
 * domain model type.
 * <p>
 * When annotating a TYPE_USE this indicates this codec should be preferred for
 * this context.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
@Documented
public @interface Codec {

    Class<? extends DataCodec> value();
}
