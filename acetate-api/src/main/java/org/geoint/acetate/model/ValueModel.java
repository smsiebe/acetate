package org.geoint.acetate.model;

import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;

/**
 * A simple data-bearing component of the data model.
 *
 * A Value differs from an Object in that it can be simply represented by a
 * single data value.
 *
 * @param <T>
 */
public interface ValueModel<T> extends ComposableModelComponent {

    /**
     * Default character codec to use for this domain object.
     *
     * @return default character codec
     */
    CharacterCodec<T> getCharacterCodec();

    /**
     * Default binary codec to use for this domain object.
     *
     * @return default binary codec
     */
    BinaryCodec<T> getBinaryCodec();
}
