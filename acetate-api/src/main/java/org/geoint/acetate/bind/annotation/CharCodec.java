package org.geoint.acetate.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.CharBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;

/**
 * Specifies the codec to use to convert data to/from character format.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
public @interface CharCodec {

    /**
     * Codec used to convert data to/from character form.
     *
     * @return character codec class
     */
    Class<? extends BufferedCodec<?, CharBuffer>> value();
}
