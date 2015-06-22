package org.geoint.acetate.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;

/**
 * Specifies the codec to use to convert data to/from binary form.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
public @interface BinaryCodec {

    /**
     * Codec used to convert data to/from binary.
     *
     * @return binary codec class
     */
    Class<? extends BufferedCodec<?, ByteBuffer>> value();
}
