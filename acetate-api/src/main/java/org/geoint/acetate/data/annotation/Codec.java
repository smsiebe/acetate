package org.geoint.acetate.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.acetate.codec.AcetateCodec;
import org.geoint.acetate.metamodel.annotation.Model;

/**
 * Annotates a data field indicating that the specified {@link AcetateCodec}
 * must be used to convert the field during binding.
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Model
public @interface Codec {

    /**
     * The {@link AcetateCodec} to use to bind the field.
     *
     * @return codec type
     */
    Class<? extends AcetateCodec<?, ?>> value();
}
