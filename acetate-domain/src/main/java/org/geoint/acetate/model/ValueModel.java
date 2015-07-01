package org.geoint.acetate.model;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.meta.annotation.Model;

/**
 * Data-bearing data model component.
 *
 * A Value differs from an Object in that a ValueModel does not contain other
 * data model components - it simply models a data value itself.
 *
 * @param <T> java class of the data value
 */
@Model(name = "value", domainName = "acetate", domainVersion = 1)
public interface ValueModel<T> extends DataModel<T> {

    /**
     * Default character codec to use for this domain object.
     *
     * @return default character codec
     */
    BufferedCodec<T, CharBuffer> getCharacterCodec();

    /**
     * Default binary codec to use for this domain object.
     *
     * @return default binary codec
     */
    BufferedCodec<T, ByteBuffer> getBinaryCodec();
}
